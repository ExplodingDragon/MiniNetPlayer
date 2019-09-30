package top.fksoft.server.udp.callback

import top.fksoft.server.udp.bean.Packet

/**
 * # 数据包监听回调方案
 *
 * @param T:Packet 自实现
 */
interface PacketListener<T:Packet>{
    fun onReceive(packet: T)
}
