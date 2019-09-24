package top.fksoft.server.udp.factory

import jdkUtils.data.StringUtils


interface HashFactory{
    /**
     * # 将可变长度字符串变成定长 ASCII 字符
     *
     *
      * @param src String 原始字符串
     * @return String 输出字符串
     */
    fun create(src: String): String

    /**
     *  # 预计生成的ByteArray 长度
     */
    val hashByteSize:Int

    /**
     * # 将生成的定长ByteArray写入目标 Array
     *
     * @param src String 原始字符串
     * @param out ByteArray 目标数组
     * @param offset Int 数组内偏移
     * @return Int 实际长度
     */
    fun createToByteArray(src: String, out: ByteArray,offset:Int): Int

    companion object{
        val default:HashFactory
         get() = DefaultHashFactory()
    }

    /**
     * # 基于CRC32的校验工具
     */
    class DefaultHashFactory:HashFactory{
        override fun create(src: String): String = StringUtils.stringCrc32Hex(src)

        override val hashByteSize: Int
            get() = 8

        override fun createToByteArray(src: String, out: ByteArray, offset: Int): Int {
            System.arraycopy(create(src).toByteArray(Charsets.US_ASCII),0,out,offset,hashByteSize)
            return hashByteSize
        }

    }
}