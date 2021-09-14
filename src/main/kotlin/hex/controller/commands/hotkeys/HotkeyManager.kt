package hex.controller.commands.hotkeys

import hex.controller.commands.SelectionTraversalModule
import hex.controller.commands.SelectionTraversalModuleProvider
import hex.controller.haccess.HexVC
import hex.controller.haccess.HexVCProvider
import hex.core.di.DiHexCore
import hex.core.di.IDialog

interface IHotkeyManager {
    fun actOnHotkey( hk: Hotkey)

}

class HotkeyManager(
    val dialog: IDialog,
    val selectionTraversalModule: SelectionTraversalModule
) : IHotkeyManager{
    var lastSelectedOffset: Long? = null
    override fun actOnHotkey(hk: Hotkey) {
        if( hk.keyCode == HotkeyCodes.VK_G && hk.pressingCtrl) {
            val desiredPositionSelect = dialog.pickOffset(lastSelectedOffset) ?: return
            lastSelectedOffset = desiredPositionSelect
            selectionTraversalModule.doSelection(desiredPositionSelect)
        }
        if( hk.keyCode == HotkeyCodes.VK_LEFT) selectionTraversalModule.goLeft()
        if( hk.keyCode == HotkeyCodes.VK_RIGHT) selectionTraversalModule.goRight()
        if( hk.keyCode == HotkeyCodes.VK_UP) selectionTraversalModule.goUp()
        if( hk.keyCode == HotkeyCodes.VK_DOWN) selectionTraversalModule.goDown()
    }

}

object HotkeyManagerProvider {
    val manager by lazy {
        HotkeyManager(
            DiHexCore.dialog,
            SelectionTraversalModuleProvider.module)
    }
}