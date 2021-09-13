package hex.jvm.view

import hex.controller.commands.ICommandRunner
import hex.controller.haccess.IHexAccessController
import hex.jvm.menu.MenuHelper
import hex.jvm.menu.MenuItem
import rb.owl.Observer
import sgui.core.components.IComponentProvider
import sgui.swing.components.SwMenuBar
import sguiSwing.components.SwComponent
import sguiSwing.components.jcomponent
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.nio.charset.Charset
import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.SwingUtilities
import kotlin.text.StringBuilder

class RootWindow(
    private val _ui : IComponentProvider,
    private val _commandRunner : ICommandRunner,
    private val _hexCont : IHexAccessController
)  : JFrame() {

    init /* Menu */ {
        val scheme = listOf(
            MenuItem("&File", null),
            MenuItem(".&Open", "File.Open")
        )

        val bar = SwMenuBar()
        MenuHelper(_commandRunner)
            .constructMenu(bar, scheme, null)
        jMenuBar = bar

    }

    private val hexTextField = JTextArea()
    private val asciiTextField = JTextArea()
    private val utf16TextField = JTextArea()

    init /* Layout */{
        this.layout = GridLayout()
        this.title = "HexTools"


        //textField.background = Color.CYAN
        hexTextField.isEditable = false
        hexTextField.font = Font("Courier New", Font.PLAIN, 14)
        hexTextField.text = ""
        asciiTextField.isEditable = false
        asciiTextField.font = Font("Courier New", Font.PLAIN, 14)
        asciiTextField.text = ""
        utf16TextField.isEditable = false
        utf16TextField.font = Font("Courier New", Font.PLAIN, 14)
        utf16TextField.text = ""


        val comp = _ui.CrossPanel {
            rows.addFlatGroup {
                add(SwComponent(hexTextField), width = 383)
                addGap(1)
                add(SwComponent(asciiTextField), width =132)
                addGap(1)
                add(SwComponent(utf16TextField), width =132)
            }
        }

        this.add(comp.jcomponent)

        SwingUtilities.invokeLater {this.size = Dimension(1400,800) }
    }

    val hexK = _hexCont.haccesObs.addObserver(Observer
    {
        val lines = _hexCont.lines
        hexTextField.text = lines
            .joinToString("\n") { byteArrayToStr(it) }
        asciiTextField.text = lines
            .joinToString("\n") { byteArrayToAscii(it) }
        utf16TextField.text = lines
            .joinToString("\n") { byteArrayToUtf16(it) }
    } , false)
}

fun byteArrayToStr(byteArray: ByteArray) : String{
    val sb = StringBuilder()
    byteArray.forEach { byte ->
        var i = byte.toInt()
        if( i < 0) i += 256 // why, Kotlin, why
        var str = i.toString(16)
        if( str.length < 2)
            str = "0$str"

        sb.append("$str ")
    }

    return sb.toString()
}

fun byteArrayToAscii( byteArray: ByteArray) : String {
    val sb = StringBuilder()
    for (byte in byteArray) {
        if(byte in 32..126)
            sb.append(Char(byte.toInt()))
        else
            sb.append('.')
    }
    return sb.toString()

}
fun byteArrayToUtf16( byteArray: ByteArray) : String {
    return String(byteArray, Charset.forName("UTF-16"))
}
