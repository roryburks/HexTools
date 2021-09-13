package hex.core.haccess

import hex.core.di.DiHexCore
import hex.core.di.IFileProvider
import rb.vectrix.IMathLayer

interface IHexAccessFactory {
    fun getAccess(file: String) : IHexAccess?
}

class HexAccessFactory(
    private val _mathLayer: IMathLayer,
    private val _fileProvider : IFileProvider
) : IHexAccessFactory{
    override fun getAccess(file: String): IHexAccess? {
        return _fileProvider.getReadWrite(file)
            ?.run { BufferedHexAccess(_mathLayer, this) }
    }
}

object HexAccessFactoryProvider {
    val factory by lazy { HexAccessFactory(DiHexCore.mathLayer, DiHexCore.fileProvider) }
}