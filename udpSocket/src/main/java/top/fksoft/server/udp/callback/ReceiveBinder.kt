package top.fksoft.server.udp.callback

import top.fksoft.server.udp.bean.Packet

 interface ReceiveBinder<T : Packet>{

    val packetListener:PacketListener<T>

    /**
     *
     * @param src ByteArray
     * @param offset Int
     * @param len Int
     * @return Packet
     * @throws Exception
     */
    @Throws(Exception::class)
    fun create(src: ByteArray, offset: Int, len: Int):Packet
}