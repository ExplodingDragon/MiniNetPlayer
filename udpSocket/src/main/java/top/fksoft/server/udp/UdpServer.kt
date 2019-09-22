package top.fksoft.server.udp

import jdkUtils.logcat.Logger
import top.fksoft.bean.NetworkInfo
import top.fksoft.server.udp.bean.Packet
import java.io.Closeable
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException
import kotlin.reflect.KClass

/**
 * # 建立一个 UDP 管理服务器
 * > 封装 的DatagramSocket
 *
 * 在指定 mtu大小时，要保证小于网络传输层最小 MTU 大小（保证数据包不分片）
 *
 */
class UdpServer(
    private val datagramSocket: DatagramSocket = DatagramSocket(31412),
    private val mtuSize: Int = DefaultPacketSize
) : Closeable {
    private val logger = Logger.getLogger(this)
    /**
     * 数据包的校验信息
     */
    private var tag:String
    set(value) {
        val byteArray = value.toByteArray(Charsets.US_ASCII)
        val size = if (byteArray.size > TAG_SIZE) 3 else byteArray.size
        for (i in 0 until size){
            tagBytes[i] = byteArray[i]
        }
    }
    get() = tagBytes.toString(Charsets.US_ASCII)
    private val tagBytes = ByteArray(TAG_SIZE)

    init {
        if (datagramSocket.isBound.not() || datagramSocket.isClosed) {
            throw SocketException("连接存在问题，无法初始化.")
        }
        tag = "PIG"
    }


    /**
     * 得到本地端口号
     */
    private val localPort by lazy {
        datagramSocket.localPort
    }

    fun <T : Packet> createNewPacket(t: KClass<out Packet> = Packet::class): Packet {
        val constructor = t.java.getDeclaredConstructor(mtuSize.javaClass)
        constructor.isAccessible = true
        return constructor.newInstance(mtuSize)

    }


    private val sendData = ByteArray(mtuSize)
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
        synchronized(sendData) {
            synchronized(packet) {
                try {
                    System.arraycopy(tagBytes, 0, sendData, 0, tagBytes.size)
                    val byteData = packet.byteData
                    System.arraycopy(byteData, 0, sendData, 0, byteData.size)
                } catch (e: Exception) {
                    logger.error("发送目标为$info 的数据包在复制中发生问题.", e)
                    return false
                }
            }
            try {
                datagramSocket.send(
                    DatagramPacket(
                        sendData,
                        0,
                        sendData.size,
                        InetAddress.getByName(info.ip),
                        info.port
                    )
                )
            } catch (e: Exception) {
                logger.error("发送目标为$info 的数据包在发送中出现问题.", e)
                return false
            }
        }
        return true
    }




    override fun close() {

    }


    companion object {
        /**
         * 标准 Internet 下 MTU 大小
         */
        const val InternetMTU = 576
        const val LocalMTU = 1492
        const val TAG_SIZE = 11

        fun calculationUdpByteSize(mtu: Int) = mtu - 20 - 8


        val DefaultPacketSize = calculationUdpByteSize(InternetMTU)

    }
}