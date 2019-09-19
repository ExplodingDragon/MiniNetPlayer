package top.fksoft.server.udp.bean

import top.fksoft.server.udp.UdpServer.Companion.DefaultPacketSize
import top.fksoft.server.udp.UdpServer.Companion.TAG_SIZE

/**
 *  # 数据包实体类
 *   针对UDP数据包
 */
open class Packet(mtuSize: Int = DefaultPacketSize){


    /**
     * 实际容纳的数据包大小，保证不分片大小
     */
    protected val maxiDataSize = mtuSize - TAG_SIZE
    val byteData = ByteArray(maxiDataSize)


}