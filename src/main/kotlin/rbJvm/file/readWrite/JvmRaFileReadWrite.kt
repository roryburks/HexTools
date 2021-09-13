package rbJvm.file.readWrite

import rb.file.readWrite.IRawReadWriteStream
import java.io.RandomAccessFile

class JvmRaFileReadWrite(
    val ra: RandomAccessFile
)  : IRawReadWriteStream{
    override val pointer: Long get() = ra.filePointer

    override fun goto(pointer: Long) { ra.seek(pointer) }

    override fun readInto(byteArray: ByteArray, offset: Int, length: Int) = ra.read(byteArray, offset, length)

    override val length: Long = ra.length()

    override fun write(byteArray: ByteArray) { ra.write(byteArray) }

    override fun close() { ra.close() }
}