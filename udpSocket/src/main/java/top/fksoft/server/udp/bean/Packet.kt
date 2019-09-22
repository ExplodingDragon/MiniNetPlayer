package top.fksoft.server.udp.bean

import top.fksoft.server.udp.UdpServer.Companion.DefaultPacketSize
import top.fksoft.server.udp.UdpServer.Companion.TAG_SIZE

/**
 *  # 数据包实体类
 *   针对UDP数据包
 */
 interface Packet{
    /**
     *  数据实际大小
     */
    var dataSize:Int
    /**
     * 原始数据存储
     */
    val byteData:ByteArray


}