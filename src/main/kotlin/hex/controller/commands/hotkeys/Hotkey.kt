package hex.controller.commands.hotkeys

data class Hotkey(
    val keyCode: Int,
    val pressingCtrl: Boolean,
    val pressingShift: Boolean,
    val pressingAlt : Boolean )
