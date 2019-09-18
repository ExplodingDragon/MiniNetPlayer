package top.fksoft.server.udp

import top.fksoft.server.udp.bean.Packet
import top.fksoft.server.udp.utils.obser.Observable
import java.io.Closeable
import java.net.DatagramSocket
import java.net.SocketException

/**
 * # 建立一个 UDP 管理服务器
 * > 封装 的DatagramSocket
 */
class UdpServer(private val datagramSocket: DatagramSocket = DatagramSocket()) : Closeable {
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

    override fun close() {

    }


    companion object{
        /**
         * 标准 Internet 下 MTU 大小
         */
        const val InternetMTU = 576
    }
}