package es.lifk.raytracer

import kotlin.math.tan

class Camera(
    val hSize: Int,
    val vSize: Int,
    val fieldOfView: Double,
    val transform: Matrix = IDENTITY_MATRIX
) {
    val pixelSize: Double
    val halfWidth: Double
    val halfHeight: Double

    init {
        val halfView = tan(fieldOfView / 2.0)
        val aspect = hSize.toDouble() / vSize.toDouble()

        if (aspect >= 1) {
            halfWidth = halfView
            halfHeight = halfView / aspect
        } else {
            halfWidth = halfView * aspect
            halfHeight = halfView
        }

        pixelSize = (halfWidth * 2) / hSize
    }

    fun rayForPixel(x: Int, y: Int): Ray {
        val xOffset = (x + 0.5) * pixelSize
        val yOffset = (y + 0.5) * pixelSize

        val worldX = halfWidth - xOffset
        val worldY = halfHeight - yOffset

        val pixel = transform.inverse() * point(worldX, worldY, -1.0)
        val origin = transform.inverse() * point(0.0, 0.0, 0.0)
        val direction = (pixel - origin).normalize()

        return Ray(origin, direction)
    }

    fun render(world: World): Canvas {
        val image = Canvas(hSize, vSize)

        (0 until vSize).forEach { y ->
            (0 until hSize).forEach { x ->
                val ray = rayForPixel(x, y)
                image[x, y] = world.colorAt(ray)
            }
        }

        return image
    }
}