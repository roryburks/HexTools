package hex.jvm.view

import hex.controller.haccess.HexVC
import hex.controller.haccess.HexVCProvider
import hex.controller.haccess.IHexView
import hex.jvm.di.DiJvm
import rb.owl.bindable.addObserver
import sgui.components.IComponent
import sgui.core.Orientation
import sgui.core.components.IComponentProvider
import sguiSwing.components.SwComponent
import java.awt.Color
import java.awt.Font
import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.geom.Point2D
import javax.swing.JTextArea
import javax.swing.SwingUtilities
import javax.swing.text.DefaultHighlighter
import javax.swing.text.Highlighter
import kotlin.math.ceil

class HexView(
    val _ui : IComponentProvider,
    val _vc : HexVC
)
    :IHexView
{
    init { _vc.view = this }

    val component : IComponent

    override fun setHexText(text: String) { hexTextField.text = text ; hexTextField.select( 0, 10) }
    override fun setAsciiText(text: String) { asciiTextField.text = text }
    override fun setOffsetText(text: String) {left.text = text }
    override fun setScrollParams(min: Int, max: Int, pos: Int, width: Int) {
        sb.minScroll = min
        sb.maxScroll = max + width - 1
        sb.scroll = pos
        sb.scrollWidth = width
    }

    override fun setCaret(caret: Int) {
        hexTextField.highlighter.removeAllHighlights()
        val color = DefaultHighlighter.DefaultHighlightPainter(Color(130, 130, 220))
        try {
            hexTextField.highlighter.addHighlight(caret, caret+2, color)
        }catch (e: Throwable){}
    }


    private val topLeft = JTextArea()
    private val top = JTextArea()
    private val topRight = JTextArea()
    private val left = JTextArea()
    private val hexTextField = JTextArea()
    private val asciiTextField = JTextArea()
    private val sb = _ui.ScrollBar(Orientation.VERTICAL, SwComponent(asciiTextField))

    init {
        topLeft.isEditable = false
        topLeft.font = Font("Courier New", Font.PLAIN, 14)
        topLeft.text = "OFFSET"

        top.isEditable = false
        top.font = Font("Courier New", Font.PLAIN, 14)
        top.text = "00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F"

        topRight.isEditable = false
        topRight.font = Font("Courier New", Font.PLAIN, 14)
        topRight.text = "ASCII"

        left.isEditable = false
        left.font = Font("Courier New", Font.PLAIN, 14)
        left.text = "ASCII"

        hexTextField.isEditable = false
        hexTextField.font = Font("Courier New", Font.PLAIN, 14)
        hexTextField.text = ""

        asciiTextField.isEditable = false
        asciiTextField.font = Font("Courier New", Font.PLAIN, 14)
        asciiTextField.text = ""

        component = _ui.CrossPanel {
            rows += {
                add(SwComponent(topLeft), width = 70)
                addGap(1)
                add(SwComponent(top), width = 390)
                addGap(1)
                add(SwComponent(topRight), width = 132)
                height = 17
            }
            rows.addGap(1)
            rows += {
                add(SwComponent(left), width = 70)
                addGap(1)
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
        sb.scrollBind.addObserver { new, old ->
            _vc.lineOffset = new.toLong()
        }

        component.onMouseWheelMoved = { evt ->
            _vc.lineOffset += evt.moveAmount
        }

        // Remove the default Mouse Listeners which handle selection and replace with own.
        val toRemove = hexTextField.mouseListeners.toList()
        toRemove.forEach {  hexTextField.removeMouseListener(it)}
        hexTextField.addMouseListener( object : MouseListener {

            override fun mousePressed(e: MouseEvent?) {
                if( e == null) return
                val pt = SwingUtilities.convertPoint(e.component, e.point, hexTextField)
                val pt2 = Point(pt.x -5, pt.y)
                val id = hexTextField.viewToModel(pt2)
                _vc.setCaret(id)
                println(id)
            }

            override fun mouseReleased(e: MouseEvent?) { }
            override fun mouseClicked(e: MouseEvent?) { }
            override fun mouseEntered(e: MouseEvent?) { }
            override fun mouseExited(e: MouseEvent?) { }

        })

    }
}

object HexViewProvider {
    fun makeView() = HexView(
        DiJvm.ui,
        HexVCProvider.vc)
}