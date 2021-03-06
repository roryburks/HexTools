package hex.jvm.menu

import hex.core.di.IDialog
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter


class Dialog : IDialog {
    override fun pickFile(): String? {
        val fc = JFileChooser()

        //fc.choosableFileFilters.forEach { fc.removeChoosableFileFilter(it) }
        //fc.addChoosableFileFilter( FileNameExtensionFilter("All", "." ))

        val result = fc.showOpenDialog(null)

        if( result == JFileChooser.APPROVE_OPTION) {
            return fc.selectedFile.absolutePath
        }
        return null
    }

    override fun pickOffset(default: Long?): Long? {
        val x = JOptionPane.showInputDialog("Select ") ?: return null
        return x.toLongOrNull(16)
    }
}