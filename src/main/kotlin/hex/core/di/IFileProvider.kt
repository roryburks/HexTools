package hex.core.di

import rb.file.readWrite.IRawReadWriteStream

interface IFileProvider {
    fun getReadWrite( file: String) : IRawReadWriteStream?
}