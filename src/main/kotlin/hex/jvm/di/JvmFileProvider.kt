package hex.jvm.di

import hex.core.di.IFileProvider
import rb.file.readWrite.IRawReadWriteStream
import rbJvm.file.readWrite.JvmRaFileReadWrite
import java.io.File
import java.io.RandomAccessFile

class JvmFileProvider  : IFileProvider{
    override fun getReadWrite(file: String): IRawReadWriteStream? {
        try {
            val jFile = File(file)
            val ra = RandomAccessFile(jFile, "rw")
            return JvmRaFileReadWrite(ra)
        }
        catch (e : Throwable)
        {
            print(e.message)
            return null
        }
    }
}