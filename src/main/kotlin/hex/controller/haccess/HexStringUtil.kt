package hex.controller.haccess

object HexStringUtil {
    fun byteArrayToStr(byteArray: ByteArray) : String{
        val sb = StringBuilder()
        val byteStrings = byteArray.map { byte ->
            var i = byte.toInt()
            if( i < 0) i += 256 // why, Kotlin, why
            var str = i.toString(16)
            if( str.length < 2)
                str = "0$str"
            str
        }

        byteStrings.forEachIndexed { i, byteStr ->
            when( i) {
                15 -> sb.append(byteStr)
                7 -> sb.append("$byteStr  ")
                else -> sb.append("$byteStr ")
            }
        }

        return sb.toString()
    }

    fun byteArrayToAscii( byteArray: ByteArray) : String {
        val sb = StringBuilder()
        for (byte in byteArray) {
            if(byte in 32..126)
                sb.append(Char(byte.toInt()))
            else
                sb.append('.')
        }
        return sb.toString()

    }

}