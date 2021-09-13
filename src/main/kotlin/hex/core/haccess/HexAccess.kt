package hex.core.haccess

import rb.file.readWrite.IRawReadWriteStream
import rb.vectrix.IMathLayer

interface IHexAccess {
    fun getLines(start :Long, count: Int = 1) : List<ByteArray>
    val length : Long
}

class BufferedHexAccess(
    private val _ml : IMathLayer,
    val io : IRawReadWriteStream)
    : IHexAccess
{
    private val lineLength = 16

    override fun getLines(start: Long, count: Int): List<ByteArray> {
        val seek = start * lineLength

        if( seek >= length) return listOf()
        if( count <= 0) return listOf()

        io.goto(seek)

        val list = mutableListOf<ByteArray>()
        val buffer = ByteArray(16)
        for ( i in start until (start+count))
        {
            val read = io.readInto(buffer, 0, 16)
            if( read == 0)
                break

            val out = ByteArray(read)
            _ml.arraycopy(buffer, 0, out, 0, read)
            list.add(out)

            if( read != 16)
                break
        }

        return list
    }

    override val length: Long by lazy { io.length }
}
