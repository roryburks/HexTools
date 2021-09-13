package hex.jvm.view

import sgui.core.components.IComponentProvider
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.SwingUtilities

class RootWindow(
    private val _ui : IComponentProvider
)  : JFrame() {

    val textField = JTextArea()

    init {
        this.layout = GridLayout()
        this.title = "HexTools"


        textField.isEditable = false
        textField.font = Font("Courier New", Font.PLAIN, 12)

        textField.text = "00 00 00 00 FF FF"

        this.add(textField)

        SwingUtilities.invokeLater {this.size = Dimension(1400,800) }
    }
}