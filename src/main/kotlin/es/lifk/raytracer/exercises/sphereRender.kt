package es.lifk.raytracer.exercises

import es.lifk.raytracer.*
import java.io.File


fun main() {
    val origin = point(0.0, 0.0, -5.0)
    val wallZ = 10.0
    val wallSize = 7.0
    val pixelSize = wallSize / 500
    val half = wallSize / 2
    val canvas = Canvas(500, 500)

    val sphere = Sphere(material = Material(Color(1.0, 0.2, 1.0)))

    val light = PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))

    (0 until 500).forEach { y ->
        val worldY = half - pixelSize * y

        (0 until 500).forEach { x ->
            val worldX = -half + pixelSize * x

            val position = point(worldX, worldY, wallZ)

            val ray = Ray(origin, (position - origin).normalize())
            val intersections = ray.intersect(sphere)

            val hit = intersections.hit()
            if (hit != null) {
                val point = ray.position(hit.t)
                val normal = hit.obj.normalAt(point)
                val eye = -ray.direction

                canvas[x, y] = lighting(hit.obj.material, light, point, eye, normal)
            }
        }
    }

    File("test3.ppm").writeText(canvasToPPM(canvas))
}