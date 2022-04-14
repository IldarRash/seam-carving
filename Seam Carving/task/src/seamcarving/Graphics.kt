package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

abstract class IGraphics(width: Int, height: Int) {
    val img: BufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    abstract fun drawDiagonal()

    abstract fun saveResult()
}
class Empty : IGraphics(0, 0) {
    override fun drawDiagonal() {
        println("do nothing")
    }

    override fun saveResult() {
        println("do nothing")
    }
}

class Square(val width: Int, val height: Int, val file: File) : IGraphics(width, height) {

    override fun drawDiagonal() {
        for (i in 0 until width) {
            for (j in 0 until height) {
                if ( i == j || width - i - 1 == j) {
                    img.setRGB(i, j, Color.red.rgb)
                }
            }
        }
    }

    override fun saveResult() {
        ImageIO.write(img, "bmp", file)
    }

}

class Rectangle(val width: Int, val height: Int, val file: File) : IGraphics(width, height) {

    override fun drawDiagonal() {
        val coef = width / height
        for (i in 0 until width) {
            for (j in 0 until height) {
                if ( i / coef == j || (width - i - 1) / coef == j) {
                    img.setRGB(i, j, Color.red.rgb)
                }
            }
        }
    }

    override fun saveResult() {
        ImageIO.write(img, "bmp", file)
    }

}

fun diagonal() {
    println("Enter rectangle width:")
    val width = readLine()!!.toInt()
    println("Enter rectangle height:")
    val height = readLine()!!.toInt()
    println("Enter output image name:")
    val fileName = readLine()!!


    val file = File(fileName)

    val graph = when {
        width != height -> Rectangle(width, height, file)
        width == height -> Square(width, height, file)
        else -> Empty()
    }

    graph.drawDiagonal()
    graph.saveResult()
}
