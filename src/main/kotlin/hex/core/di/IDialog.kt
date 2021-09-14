package hex.core.di

interface IDialog {
    fun pickFile() : String?

    fun pickOffset(default: Long?) :  Long?
}