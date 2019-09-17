package top.fksoft.server.udp.utils.obser

import java.io.Closeable
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * # 观察者的自定义
 */
class Observable <T> constructor(private val threadPool: Executor = Executors.newCachedThreadPool()):Closeable{
    private val array = Vector<Observer<T>>()

    @Synchronized
    fun addObserver(observer: Observer<T>) {
        if (!array.contains(observer)) {
            array.addElement(observer)
        }
    }


    @Synchronized
    fun deleteObserver(observer: Observer<T>) {
        array.removeElement(observer)
    }


    /**
     *
     */
    fun notifyObservers(t: T) {
        val arr:Array<Any> = kotlin.run {
            synchronized(this){
                 array.toArray()
            }
        }
        arr.forEach {
            val observer = it as Observer<T>
            threadPool.execute {
                observer.update(t)
            }
        }
    }

    @Synchronized
    override fun close() {
        array.clear()
    }
}