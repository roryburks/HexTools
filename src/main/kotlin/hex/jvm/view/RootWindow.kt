package hex.jvm.view

import hex.controller.commands.ICommandRunner
import hex.controller.commands.hotkeys.IHotkeyManager
import hex.controller.haccess.IHexAccessController
import hex.jvm.menu.MenuHelper
import hex.jvm.menu.MenuItem
import hex.jvm.system.keyboard.JvmKeyboardHelper
import rb.owl.Observer
import sgui.core.Orientation
import sgui.core.components.IComponentProvider
import sgui.swing.components.SwMenuBar
import sguiSwing.components.SwComponent
import sguiSwing.components.jcomponent
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.KeyboardFocusManager
import java.awt.event.KeyEvent
import java.nio.charset.Charset
import javax.swing.JFrame
import javax.swing.JTextArea
import javax.swing.SwingUtilities
import kotlin.text.StringBuilder

class RootWindow(
    private val _ui : IComponentProvider,
    private val _commandRunner : ICommandRunner,
    private val _hexCont : IHexAccessController,
    private val _hotkeyManager : IHotkeyManager
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

    init /* Layout */{
        this.layout = GridLayout()
        this.title = "HexTools"

        val hexView = HexViewProvider.makeView()

        this.add(hexView.component.jcomponent)

        SwingUtilities.invokeLater {this.size = Dimension(1400,800) }
    }

    init /* Keyboard */ {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher { evt ->
            if(focusOwner == null )
                return@addKeyEventDispatcher false

            if( evt.id == KeyEvent.KEY_PRESSED) {
                _hotkeyManager.actOnHotkey(JvmKeyboardHelper.map(evt))
            }


            false
        }
    }
}