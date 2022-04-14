package seamcarving
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.graalvm.compiler.lir.asm.CompilationResultBuilderFactory.Default
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt

fun energy(inPut: String, outPut: String) {
    val bufferedImage = ImageIO.read(File(inPut))
    val list: MutableList<String> = mutableListOf()
    var maxEnergyValue = 0.0

    for (x in 0 until bufferedImage.width) {
        for (y in 0 until bufferedImage.height) {

            val delta1 = deltaX(x, y, bufferedImage)
            val delta2 = deltaY(x, y, bufferedImage)

            val energy = sqrt(delta1 + delta2)

            if (energy > maxEnergyValue) {
                maxEnergyValue = energy
            }

            list.add("$x $y $energy")


        }
    }

    for (i in list) {
        val (x, y, energy) = i.split(" ").map { it.toDouble() }

        val intensity = (255.0 * energy / maxEnergyValue).toInt()
        val colorNew = Color(intensity, intensity, intensity)
        bufferedImage.setRGB(x.toInt(), y.toInt(), colorNew.rgb)
    }

    ImageIO.write(bufferedImage, "bmp", File(outPut))
}

fun main() = runBlocking {
    for (i in 200..1000 step 200) {
        launch {
            delay(1000L - i)
            print(i)
        }
    }
}

fun deltaY(x: Int, y: Int, image: BufferedImage): Double {
    val posY = when(y) {
        0 -> 1
        image.height - 1 -> image.height - 2
        else -> y
    }
    val top = Color(image.getRGB(x, posY - 1))
    val low = Color(image.getRGB(x, posY + 1))
    return (top.red.toDouble() - low.red.toDouble()).pow(2) + (top.green.toDouble() - low.green.toDouble()).pow(
        2
    ) + (top.blue.toDouble() - low.blue.toDouble()).pow(2)
}


fun deltaX(x: Int, y: Int, image: BufferedImage): Double {
    val posX = when(x) {
        0 -> 1
        image.width - 1 -> image.width - 2
        else -> x
    }
    val left = Color(image.getRGB(posX - 1, y))
    val right = Color(image.getRGB(posX + 1, y))

    return (left.red.toDouble() - right.red.toDouble()).pow(2) + (left.green.toDouble() - right.green.toDouble()).pow(
        2
    ) + (left.blue.toDouble() - right.blue.toDouble()).pow(2)
}
