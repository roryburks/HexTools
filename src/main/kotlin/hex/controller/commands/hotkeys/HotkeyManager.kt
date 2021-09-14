package hex.controller.commands.hotkeys

interface IHotkeyManager {
    fun actOnHotkey( hk: Hotkey)

}

class HotkeyManager : IHotkeyManager{
    override fun actOnHotkey(hk: Hotkey) {
        println("A")
    }

}

object HotkeyManagerProvider {
    val manager = HotkeyManager()
}