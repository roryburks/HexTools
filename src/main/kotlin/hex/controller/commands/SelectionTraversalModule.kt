package hex.controller.commands

import hex.controller.haccess.HexVC
import hex.controller.haccess.HexVCProvider
import rb.vectrix.mathUtil.MathUtil

interface ISelectionTraversalModule {
    fun goto(selection: Long)
    fun goUp()
    fun goDown()
    fun goLeft()
    fun goRight()

}

class SelectionTraversalModule(
    val hexVc: HexVC ) : ISelectionTraversalModule
{
    fun doSelection(selection: Long) {
        val rowLocationOfSelect = selection / 16

        // Make sure that the Selected position is visible
        val currentRowOffset = hexVc.lineOffset
        if( rowLocationOfSelect < currentRowOffset) {
            hexVc.lineOffset = rowLocationOfSelect
        }
        else if( rowLocationOfSelect >= currentRowOffset + hexVc.displayLineCount) {
            hexVc.lineOffset = rowLocationOfSelect - hexVc.displayLineCount + 1
        }

        // Update the selection
        hexVc.selected = selection
    }

    override fun goto(selection: Long) {
        val len = hexVc.length
        val sel = if( len == 0L) 0L
            else MathUtil.clip(0L, selection, len)
        doSelection(sel)
    }

    override fun goUp() {
        val cur = hexVc.selected ?: return
        goto(cur - 16)
    }

    override fun goDown() {
        val cur = hexVc.selected ?: return
        goto(cur + 16)
    }

    override fun goLeft() {
        val cur = hexVc.selected ?: return
        goto(cur - 1)
    }

    override fun goRight() {
        val cur = hexVc.selected ?: return
        goto(cur + 1)
    }
}

object SelectionTraversalModuleProvider {
    val module by lazy { SelectionTraversalModule(HexVCProvider.vc) }
}