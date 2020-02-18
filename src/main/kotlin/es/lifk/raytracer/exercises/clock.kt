package es.lifk.raytracer.exercises

import es.lifk.raytracer.Canvas
import es.lifk.raytracer.Color
import es.lifk.raytracer.canvasToPPM
import es.lifk.raytracer.point
import es.lifk.raytracer.rotationZ
import java.io.File
import kotlin.math.PI
import kotlin.math.roundToInt

fun main() {
    renderClock()
}

fun renderClock() {
    val canvas = Canvas(500, 500)

    (0 until 12).forEach {
        val point = point(0.0, 1.0, 0.0).transform(
            rotation = rotationZ((PI / 6) * it)
        )

        canvas[
                (point.x * 50.0).roundToInt() + 250,
                (point.y * 50.0).roundToInt() + 250
        ] = Color(1.0, 0.0, 0.0)

        File("test2.ppm").writeText(canvasToPPM(canvas))
    }

}