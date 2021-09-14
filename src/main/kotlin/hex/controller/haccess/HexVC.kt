package hex.controller.haccess

import rb.owl.Observer
import rb.vectrix.mathUtil.MathUtil
import rb.vectrix.mathUtil.i

class HexVC(
    val _controller: IHexAccessController
)
{
    lateinit var view : IHexView

    var displayLineCount : Int = 20
        set(value) { field = value; _rebuild()}
    val totalNumLines : Long get() = (_controller.length + 15) / 16
    var lineOffset : Long = 0
        set(value) {
            val to = if( totalNumLines == 0L) 0L
                    else MathUtil.clip(0L, value, totalNumLines -1)
            if(field != to) { field = to; _rebuild()}}
    var offsetPointer : Int = 0



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
    }

    // Binds
    val hexK = _controller.haccesObs.addObserver(Observer { _rebuild() }, false)
}

interface IHexView {
    fun setHexText(text: String)
    fun setAsciiText(text: String)
    fun setOffsetText(text: String)
    fun setScrollParams( min: Int, max: Int, pos: Int, width: Int)
}

object HexVCProvider {
    val vc by lazy { HexVC(HexAccessControllerProvider.controller) }
}
