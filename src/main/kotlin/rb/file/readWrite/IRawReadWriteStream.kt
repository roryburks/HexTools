package rb.file.readWrite

interface IRawReadWriteStream {
    val pointer: Long
    fun goto(pointer: Long)
    fun readInto( byteArray: ByteArray, offset: Int = 0, length: Int = byteArray.size) : Int
    val length: Long
    fun write(byteArray: ByteArray)
    fun close()
}

