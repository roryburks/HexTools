package hex.jvm.di

import sgui.core.components.IComponentProvider
import sgui.swing.SwingComponentProvider

object DiJvm {
    val ui : IComponentProvider = SwingComponentProvider
}