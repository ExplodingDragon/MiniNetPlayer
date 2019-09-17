package top.fksoft.server.udp.utils.obser

interface Observer<T> {
    fun update(t: T)
}