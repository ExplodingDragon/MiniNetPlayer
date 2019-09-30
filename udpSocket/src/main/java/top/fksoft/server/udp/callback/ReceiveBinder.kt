package top.fksoft.server.udp.callback

class ReceiveBinder (private val packetListener: PacketListener){
    val byteData = ByteArray(1500)
    var size = 0
    

    /**
     * @param src ByteArray
     * @param offset Int
     * @param len Int
     */
    final fun copy(src:ByteArray,offset:Int,len :Int){

        size = len
        System.arraycopy(src,offset,byteData,0,len)
    }
}