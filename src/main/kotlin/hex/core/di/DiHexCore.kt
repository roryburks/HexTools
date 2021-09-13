package hex.core.di

import rb.vectrix.IMathLayer

object DiHexCore {
    val mathLayer by lazy { mathLayerLazy.value }
    lateinit var  mathLayerLazy: Lazy<IMathLayer>

    val fileProvider by lazy { fileProviderLazy.value }
    lateinit var fileProviderLazy : Lazy<IFileProvider>
}