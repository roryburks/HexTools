package hex.core.di

import rb.vectrix.IMathLayer

object DiHexCore {
    lateinit var  mathLayerLazy: Lazy<IMathLayer>
    val mathLayer by lazy { mathLayerLazy.value }

    lateinit var fileProviderLazy : Lazy<IFileProvider>
    val fileProvider by lazy { fileProviderLazy.value }

    lateinit var dialogLazy : Lazy<IDialog>
    val dialog by lazy { dialogLazy.value }
}