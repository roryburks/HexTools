package hex.jvm.menu

import hex.controller.commands.ICommandRunner
import javax.swing.*

class MenuHelper(
    private val _commandRunner : ICommandRunner
) {
    fun constructMenu(root: JComponent, menuScheme: List<MenuItem>, extra: Any? = null) {
        val isMenuBar = root is JMenuBar
        val isPopup = root is JPopupMenu
        val activeRootTree = MutableList<JMenuItem?>(10) { null }

        // Atempt to construct menu from parsed data in menu_scheme
        var activeDepth = 0
        menuScheme.forEachIndexed { index, item ->
            var mnemonic = 0.toChar()

            // Determine the depth of the node and crop off the extra .'s
            var depth = _imCountLevel(item.lexicon)
            var lexiocon = item.lexicon.substring(depth)

            if( depth > activeDepth) {
                println("Bad Menu Scheme.")
                depth = activeDepth
            }
            activeDepth = depth+1

            if( lexiocon == "-") {
                if( depth == 0) {
                    if( isPopup) (root as JPopupMenu).addSeparator()
                }
                else
                    (activeRootTree[depth-1] as JMenu).addSeparator()

                activeDepth--
            }
            else {
                // Detect the Mnemonic
                val mnemIndex = lexiocon.indexOf('&')
                if( mnemIndex != -1 && mnemIndex != lexiocon.length-1) {
                    mnemonic = lexiocon[mnemIndex+1]
                    lexiocon = lexiocon.substring(0, mnemIndex) + lexiocon.substring(mnemIndex+1)
                }

                // Determine if it needs to be a Menu (which contains other options nested in it)
                //	or a plain MenuItem (which doesn't)
                val node = when {
                    (depth != 0 || !isMenuBar) && (index+1 == menuScheme.size || _imCountLevel(menuScheme[index+1].lexicon) <= depth)
                    -> JMenuItem(lexiocon).also { it.isEnabled = item.enabled }
                    else -> JMenu(lexiocon).also { it.isEnabled = item.enabled }
                }
                if( mnemonic != 0.toChar())
                    node.setMnemonic(mnemonic)

                if( item.command != null)
                    node.addActionListener { _commandRunner.runCommand(item.command, item.obj) }

                // Add the MenuItem into the appropriate workspace
                when {
                    depth == 0 -> root.add( node)
                    else -> activeRootTree[depth-1]!!.add(node)
                }

                activeRootTree[depth] = node
            }
        }
    }
    private val MAX_LEVEL = 10
    private fun _imCountLevel(s: String): Int {
        var r = 0
        while (r < s.length && s[r] == '.')
            r++
        return Math.min(r, MAX_LEVEL)
    }

}