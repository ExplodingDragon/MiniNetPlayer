package top.fksoft.server.udp.bean

import top.fksoft.server.udp.UdpServer

/**
 * # 数据包实体类
 *
 * @property dataLen Int 数据包最大长度，初始化用
 * @property type ByteArray 数据包类型
 * @property dataSize Int 使用的长度
 * @property byteData ByteArray 数据包内容
 * @constructor
 */
 open class Packet constructor(dataLen:Int){

    /**
     * 数据包类型判断
     */
    val type = ByteArray(UdpServer.TYPE_SIZE)

    /**
     * #指定实际类型
     *
     * @param hash String 哈希值
     */
    protected fun setType(hash: String) {
        val array = hash.toByteArray(Charsets.US_ASCII)
        System.arraycopy(array,0,type,0,if (type.size > array.size)array.size else type.size )
    }

    /**
     *  数据实际大小
     */
    var dataSize:Int = 0
    /**
     * 原始数据存储
     */
    open val byteData:ByteArray = ByteArray(dataLen)

}