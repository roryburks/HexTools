package hex.controller.haccess

import rb.owl.Observer
import rb.vectrix.mathUtil.MathUtil

class HexVC(
    val _controller: IHexAccessController
)
{
    lateinit var view : IHexView

    var displayLineCount : Int = 20
        set(value) { field = value; _rebuild()}
    val totalNumLines : Long get() = (_controller.length + 15) / 16
    var lineOffset : Long = 0
    var offsetPointer : Int = 0



    private fun _rebuild() {
        val numLines = totalNumLines
        val lineOffset = MathUtil.clip(0L, lineOffset, numLines)
        val lines= _controller.getLines(lineOffset, displayLineCount)

        val hexText = lines
            .joinToString("\n") { HexStringUtil.byteArrayToStr(it)  }
        view.setHexText(hexText)
        val asciiText = lines
            .joinToString("\n") { HexStringUtil.byteArrayToAscii(it)}
        view.setAsciiText(asciiText)

    }

    // Binds
    val hexK = _controller.haccesObs.addObserver(Observer { _rebuild() }, false)
}

interface IHexView {
    fun setHexText(text: String)
    fun setAsciiText(text: String)
}

object HexVCProvider {
    val vc by lazy { HexVC(HexAccessControllerProvider.controller) }
}
