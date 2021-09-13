package hex.jvm.di

import hex.core.di.DiHexCore
import hex.jvm.menu.Dialog

object JvmDiHexCore {
    fun init() {
        DiHexCore.dialogLazy = lazy { Dialog() }
        DiHexCore.mathLayerLazy = lazy { rb.vectrix.BaseMathLayer }
        DiHexCore.fileProviderLazy = lazy { JvmFileProvider() }
    }
}