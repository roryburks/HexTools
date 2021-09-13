package rbJvm.glow.awt

import rb.glow.Color
import rb.glow.IGraphicsContext
import rb.glow.img.RawImage
import java.awt.image.BufferedImage

class ImageBI(
    bi: BufferedImage

) : RawImage {
    val bi: BufferedImage get() = TODO("Not yet implemented")

    override val graphics: IGraphicsContext
        get() = TODO("Not yet implemented")

    override fun flush() {
        TODO("Not yet implemented")
    }

    override val width: Int
        get() = TODO("Not yet implemented")
    override val height: Int
        get() = TODO("Not yet implemented")
    override val byteSize: Int
        get() = TODO("Not yet implemented")

    override fun deepCopy(): RawImage {
        TODO("Not yet implemented")
    }

    override fun getColor(x: Int, y: Int): Color {
        TODO("Not yet implemented")
    }
}