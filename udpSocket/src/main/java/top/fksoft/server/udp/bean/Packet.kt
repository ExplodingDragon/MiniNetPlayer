package top.fksoft.server.udp.bean

import top.fksoft.server.udp.UdpServer

/**
 *  # 数据包实体类
 *   针对UDP数据包
 */
open class Packet(private val mtuSize: Int = UdpServer.calculationUdpByteSize(UdpServer.InternetMTU)) {
    protected val byteArray = ByteArray(mtuSize)

}