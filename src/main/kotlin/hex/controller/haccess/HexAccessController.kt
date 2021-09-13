package hex.controller.haccess

import hex.core.haccess.IHexAccess
import rb.owl.IObservable
import rb.owl.IObserver
import rb.owl.Observable

interface IHexAccessController {
    fun setAccess(hexAccess: IHexAccess)
    val lines : List<ByteArray>

    val haccesObs : IObservable<()->Unit>
}

class HexAccessController : IHexAccessController{
    private var access: IHexAccess? = null

    override fun setAccess(hexAccess: IHexAccess) {
        access = hexAccess
        haccesObs.trigger { it.invoke() }
    }

    override val lines: List<ByteArray>
        get() {
            val access = access ?: return emptyList()

            return access.getLines(0, 20)
        }

    override val haccesObs = Observable<()->Unit>()
}

object HexAccessControllerProvider {
    val controller = HexAccessController()
}