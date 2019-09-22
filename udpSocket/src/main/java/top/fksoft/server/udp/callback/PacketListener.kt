package top.fksoft.server.udp.callback

import top.fksoft.server.udp.bean.Packet

/**
 * @author Explo
 */
interface PacketListener{
    fun onReceive(packet: Packet)
}
