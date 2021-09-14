package hex.jvm.system.keyboard

import hex.controller.commands.hotkeys.Hotkey
import java.awt.event.KeyEvent

object JvmKeyboardHelper {
    fun map(evt: KeyEvent) : Hotkey {
        // TODO: If HotkeyCodes ever differe from KeyEvent.VK_Codes, do mapping here

        return Hotkey(
            evt.keyCode,
            evt.isControlDown,
            evt.isShiftDown,
            evt.isAltDown )
    }
}