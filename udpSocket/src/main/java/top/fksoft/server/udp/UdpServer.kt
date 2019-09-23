package top.fksoft.server.udp

import jdkUtils.data.AtomicUtils
import jdkUtils.logcat.Logger
import top.fksoft.bean.NetworkInfo
import top.fksoft.server.udp.bean.Packet
import top.fksoft.server.udp.callback.Binder
import java.io.Closeable
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * # 建立一个 UDP 管理服务器
 * > 封装 的DatagramSocket
 *
 * 此udp传输无加密
 *
 * 在指定 mtu大小时，要保证小于网络传输层最小 MTU 大小（保证数据包不分片）
 *
 * ``` bash
 * # 数据包结构
 * [off:0,size:2 = 校验头][off:2,size:8 = 子类型确定][off:10,size:2 = 数据包真实长度][off:10,size : -1 = 数据内容]
 * ```
 */
class UdpServer(
    private val datagramSocket: DatagramSocket = DatagramSocket(31412),
    private val mtuSize: Int = DefaultPacketSize,
    private val threadPool: ExecutorService = Executors.newCachedThreadPool()
) : Closeable {
    /**
     * 是否关闭服务器
     */
    val isClosed: Boolean
        get() = datagramSocket.isBound.not() || datagramSocket.isClosed
    private val tagBytes = ByteArray(TAG_SIZE)
    private val logger = Logger.getLogger(this)

    private val receiveMap = ConcurrentHashMap<String,Binder>()
    /**
     *  发送中转
     */
    private val sendPacketData = ByteArray(mtuSize);
    /**
     * 最大可容纳数据包大小
     */
    val dataLength: Int
        get() = mtuSize - HEADER_SIZE
    var tag: String
        set(value) {
            val byteArray = value.toByteArray(Charsets.US_ASCII)
            val size = if (byteArray.size > TAG_SIZE) TAG_SIZE else byteArray.size
            for (i in 0 until size) {
                tagBytes[i] = byteArray[i]
            }
            System.arraycopy(tagBytes, 0, sendPacketData, 0, TAG_SIZE)
        }
        get() = tagBytes.toString(Charsets.US_ASCII)

    init {
        if (isClosed) {
            throw SocketException("连接存在问题，无法初始化.")
        }
        tag = "AD"
    }


    /**
     * 得到本地端口号
     */
    private val localPort by lazy {
        datagramSocket.localPort
    }


    /**
     * # 发送数据包
     *
     * > 将封装好的数据包发送到目标服务器
     *
     * @param packet Packet 数据包
     * @param info NetworkInfo 远程服务器ip + 端口
     * @return Boolean 是否由系统发送
     */
    @Synchronized
    fun sendPacket(packet: Packet, info: NetworkInfo): Boolean {
        try {
            if (isClosed) {
                throw IOException("服务器已经关闭.")
            }
            synchronized(sendPacketData) {
                System.arraycopy(packet.type, 0, sendPacketData, TAG_SIZE, TYPE_SIZE)
                //复制头
                System.arraycopy(
                    AtomicUtils.shortToBytes(packet.dataSize.toShort()),
                    0,
                    sendPacketData,
                    TAG_SIZE + TYPE_SIZE,
                    2
                )
                //复制数据实际大小
                datagramSocket.send(
                    DatagramPacket(
                        sendPacketData,
                        sendPacketData.size,
                        InetAddress.getByName(info.ip),
                        info.port
                    )
                )
                //进行数据包发送
            }

        } catch (e: Exception) {
            logger.error("此实例在发送数据到$info 时出现问题.", e)
            return false
        }
        logger.info("发送数据包到$info 完成.")
        return true
    }

    @Synchronized
    fun bindReceive(hash: String, binder: Binder) {

    }




    override fun close() {

    }


    companion object {
        /**
         * 标准 Internet 下 MTU 大小
         */
        const val InternetMTU = 576
        const val LocalMTU = 1492
        const val TAG_SIZE = 2

        /**
         * CRC32 长度
         * 采用crc32判断类型
         */
        const val TYPE_SIZE = 8

        const val HEADER_SIZE = TAG_SIZE + TYPE_SIZE + 2

        fun calculationUdpByteSize(mtu: Int) = mtu - 20 - 8


        val DefaultPacketSize = calculationUdpByteSize(InternetMTU)

    }
}