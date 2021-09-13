package hex.jvm.menu

class MenuItem(
    val lexicon : String,
    val command : String?,
    val obj : Any? = null,
    val enabled: Boolean = true)