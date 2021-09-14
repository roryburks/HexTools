package hex.controller.haccess

import rb.owl.Observer
import rb.vectrix.mathUtil.MathUtil
import rb.vectrix.mathUtil.i

class HexVC(
    val _controller: IHexAccessController
)
{
    lateinit var view : IHexView

    val totalNumLines : Long get() = (_controller.length + 15) / 16
    val length : Long get() = _controller.length

    var displayLineCount : Int = 20
        set(value) { field = value; _rebuild()}
    var lineOffset : Long = 0
        set(value) {
            val to = if( totalNumLines == 0L) 0L
                    else MathUtil.clip(0L, value, totalNumLines -1)
            if(field != to) { field = to; _rebuild()}}
    var selected:  Long? = null
        set(value)
        {
            val to: Long? =
                if( value == null)
                    null
                else {
                    if (length == 0L) 0
                    else MathUtil.clip(0L, value, length)
                }
            if( field != to) {
                field = to
                setToSelected()
            }
        }


    // Selection
    fun setCaret( caret: Int) {
        val row = caret / 49
        val hOffset: Int
        val rem = caret % 49
        if( rem < 24) {
            hOffset = rem / 3
        }
        else {
            hOffset = (rem - 1) / 3
        }
        selected = (row + lineOffset)*16 + hOffset

    }

    // Internal Functions
    private fun _rebuild() {
        val numLines = totalNumLines
        val lineOffset = MathUtil.clip(0L, lineOffset, numLines-1)
        val lines= _controller.getLines(lineOffset, displayLineCount)

        val hexText = lines
            .joinToString("\n") { HexStringUtil.byteArrayToStr(it)  }
        view.setHexText(hexText)
        val asciiText = lines
            .joinToString("\n") { HexStringUtil.byteArrayToAscii(it)}
        view.setAsciiText(asciiText)

        val offsetText = (lineOffset until lineOffset+displayLineCount)
            .joinToString("\n") {
                val offset = it * 16
                var str = offset.toString(16)
                while(str.length < 8) str = "0$str"
                "$str "
            }
        view.setOffsetText(offsetText)


        view.setScrollParams(0, numLines.i, lineOffset.i, displayLineCount)
        setToSelected()
    }

    private fun setToSelected(){
        val sel = selected ?: return
        val off = sel - (lineOffset*16)
        val line = (off / 16).toInt()
        val hexOff = (off % 16).toInt()
        val hOff = if( hexOff > 7) 1 + 3*hexOff
            else 3*hexOff
        view.setCaret(hOff + line*49)


    }

    // Binds
    val hexK = _controller.haccesObs.addObserver(Observer
    {
        selected = null
        lineOffset = 0
        _rebuild()
    }, false)
}

interface IHexView {
    fun setHexText(text: String)
    fun setAsciiText(text: String)
    fun setOffsetText(text: String)
    fun setScrollParams( min: Int, max: Int, pos: Int, width: Int)
    fun setCaret(caret: Int)
}

object HexVCProvider {
    val vc by lazy { HexVC(HexAccessControllerProvider.controller) }
}
