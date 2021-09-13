package hex.jvm.view

import hex.controller.haccess.HexVC
import hex.controller.haccess.HexVCProvider
import hex.controller.haccess.IHexView
import hex.jvm.di.DiJvm
import sgui.components.IComponent
import sgui.core.Orientation
import sgui.core.components.IComponentProvider
import sguiSwing.components.SwComponent
import java.awt.Font
import javax.swing.JTextArea

class HexView(
    val _ui : IComponentProvider,
    val _vc : HexVC
)
    :IHexView
{
    init { _vc.view = this }

    val component : IComponent

    override fun setHexText(text: String) { hexTextField.text = text }
    override fun setAsciiText(text: String) { asciiTextField.text = text }


    private val hexTextField = JTextArea()
    private val asciiTextField = JTextArea()

    init {
        hexTextField.isEditable = false
        hexTextField.font = Font("Courier New", Font.PLAIN, 14)
        hexTextField.text = ""
        asciiTextField.isEditable = false
        asciiTextField.font = Font("Courier New", Font.PLAIN, 14)
        asciiTextField.text = ""

        val sb = _ui.ScrollBar(Orientation.VERTICAL, SwComponent(asciiTextField))

        component = _ui.CrossPanel {
            rows.addFlatGroup {
                add(SwComponent(hexTextField), width = 390)
                addGap(1)
                add(SwComponent(asciiTextField), width = 132)
                addGap(2)
                add(sb)
            }
        }
    }

    init {
        SwComponent(hexTextField).onResize += {
            val h = hexTextField.height
            val calcNumLines = h / 17
            _vc.displayLineCount = calcNumLines
        }
    }
}

object HexViewProvider {
    fun makeView() = HexView(
        DiJvm.ui,
        HexVCProvider.vc)
}