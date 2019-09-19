package top.fksoft.server.udp

import jdkUtils.logcat.Logger
import top.fksoft.bean.NetworkInfo
import top.fksoft.server.udp.bean.Packet
import top.fksoft.server.udp.utils.obser.Observable
import java.io.Closeable
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException
import kotlin.reflect.KClass

/**
 * # 建立一个 UDP 管理服务器
 * > 封装 的DatagramSocket
 */
class UdpServer(
    private val datagramSocket: DatagramSocket = DatagramSocket(31412),
    private val mtuSize: Int = DefaultPacketSize
) : Closeable {
    private val logger= Logger.getLogger(this)
    val tag = byteArrayOf('H'.toByte(), 'A'.toByte(), 'T'.toByte())
    private val observable = Observable<Packet>()

    init {
        if (datagramSocket.isBound.not() || datagramSocket.isClosed) {
            throw SocketException("连接存在问题，无法初始化.")
        }
    }


    /**
     * 得到本地端口号
     */
    private val localPort by lazy {
        datagramSocket.localPort
    }

    fun <T : Packet> createNewPacket(t: KClass<T>): T {
        val constructor = t.java.getDeclaredConstructor(mtuSize.javaClass)
        constructor.isAccessible = true
        return constructor.newInstance(mtuSize)
    }



    private val sendData = ByteArray(mtuSize)
    /**
     * # 发送数据包
     *
     * @param packet Packet
     * @param info NetworkInfo
     * @return Boolean
     */
    @Synchronized
    fun sendPacket(packet: Packet, info: NetworkInfo) :Boolean{
        synchronized(sendData) {
            synchronized(packet) {
               try {
                   System.arraycopy(tag,0,sendData,0,tag.size)
                   val byteData = packet.byteData
                   System.arraycopy(byteData,0,sendData,0,byteData.size)
               }catch (e:Exception){
                    logger.error("发送目标为$info 的数据包在复制中发生问题.",e)
                   return false
               }
            }
            try {
                datagramSocket.send(DatagramPacket(sendData,0,sendData.size, InetAddress.getByName(info.ip),info.port))
            }catch (e:Exception){
                logger.error("发送目标为$info 的数据包在发送中出现问题.",e)
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
        const val TAG_SIZE = 3

        fun calculationUdpByteSize(mtu: Int) = mtu - 20 - 8


        val DefaultPacketSize = calculationUdpByteSize(InternetMTU)

    }
}