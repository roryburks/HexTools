package hex.controller.commands.hotkeys

import hex.controller.haccess.HexVC
import hex.controller.haccess.HexVCProvider
import hex.core.di.DiHexCore
import hex.core.di.IDialog

interface IHotkeyManager {
    fun actOnHotkey( hk: Hotkey)

}

class HotkeyManager(
    val dialog: IDialog,
    val hexVc: HexVC
) : IHotkeyManager{
    var lastSelectedOffset: Long? = null
    override fun actOnHotkey(hk: Hotkey) {
        println("A")

        if( hk.keyCode == HotkeyCodes.VK_G && hk.pressingCtrl) {
            val offset = dialog.pickOffset(lastSelectedOffset) ?: return
            val rowOffsetIsIn = offset / 16
            val curRow = hexVc.lineOffset
            if( rowOffsetIsIn < curRow) {
                hexVc.lineOffset = rowOffsetIsIn
            }
            else if( rowOffsetIsIn >= curRow + hexVc.displayLineCount) {
                hexVc.lineOffset = rowOffsetIsIn - hexVc.displayLineCount + 1
            }
            lastSelectedOffset = offset
            hexVc.selected = lastSelectedOffset
        }
    }

}

object HotkeyManagerProvider {
    val manager by lazy {
        HotkeyManager(
            DiHexCore.dialog,
            HexVCProvider.vc)
    }
}