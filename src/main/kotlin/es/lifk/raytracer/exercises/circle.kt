package es.lifk.raytracer.exercises

import es.lifk.raytracer.*
import java.io.File


fun main() {
    val origin = point(0.0, 0.0, -5.0)
    val wallZ = 10.0
    val wallSize = 7.0
    val pixelSize = wallSize / 100
    val half = wallSize / 2
    val canvas = Canvas(100, 100)
    val color = Color(1.0, 0.0, 0.0)

    val sphere = Sphere()

    (0 until 100).forEach { y ->
        val worldY = half - pixelSize * y

        (0 until 100).forEach { x ->
            val worldX = -half + pixelSize * x

            val position = point(worldX, worldY, wallZ)

            val intersections = Ray(origin, (position - origin).normalize()).intersect(sphere)

            if (intersections.hit() != null) {
                canvas[x, y] = color
            }
        }
    }

    File("test3.ppm").writeText(canvasToPPM(canvas))
}