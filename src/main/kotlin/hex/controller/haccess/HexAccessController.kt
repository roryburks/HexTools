package hex.controller.haccess

import hex.core.haccess.IHexAccess
import rb.owl.IObservable
import rb.owl.IObserver
import rb.owl.Observable

interface IHexAccessController {
    fun setAccess(hexAccess: IHexAccess)
    fun getLines( start: Long, count: Int) : List<ByteArray>
    val haccesObs : IObservable<()->Unit>
    val length : Long
}

class HexAccessController : IHexAccessController{
    private var access: IHexAccess? = null

    override fun setAccess(hexAccess: IHexAccess) {
        access = hexAccess
        haccesObs.trigger { it.invoke() }
    }

    override fun getLines(start: Long, count: Int)  : List<ByteArray>{
        val access = access ?: return emptyList()
        return access.getLines(start, count)
    }

    override val haccesObs = Observable<()->Unit>()
    override val length: Long get() = access?.length ?: 0L
}

object HexAccessControllerProvider {
    val controller = HexAccessController()
}