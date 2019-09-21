import jdkUtils.data.AtomicUtils
import jdkUtils.data.StringUtils
import jdkUtils.logcat.Logger

class Test {
    private val logger = Logger.getLogger(this)
    @org.junit.Test
    fun run() {
        for (index in 1 until 10){
            println(index)//输出0..9
        }
    }
}
