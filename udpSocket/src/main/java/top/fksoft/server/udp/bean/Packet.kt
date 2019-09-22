package top.fksoft.server.udp.bean

import top.fksoft.server.udp.UdpServer.Companion.DefaultPacketSize
import top.fksoft.server.udp.UdpServer.Companion.TAG_SIZE

/**
 *  # 数据包实体类
 *   针对UDP数据包
 */
open class Packet(mtuSize: Int = DefaultPacketSize){


    val maxiDataSize = mtuSize - TAG_SIZE
    val byteData = ByteArray(maxiDataSize)


}