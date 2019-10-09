package top.fksoft.server.udp.factory

import top.fksoft.server.udp.bean.Packet
import kotlin.reflect.KClass

/**
 * @author Explo
 */
interface NewInstanceFactory<T:Packet>{


    fun newInstance(clazz: KClass<T>):T


    companion object{
        val default
            get() = DefaultNewInstanceFactory() as NewInstanceFactory<Packet>


    }

    class DefaultNewInstanceFactory :NewInstanceFactory<Packet>{
        override fun newInstance(clazz: KClass<Packet>)  = clazz.java.newInstance()


    }
}
