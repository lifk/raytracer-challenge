package es.lifk.raytracer

import kotlin.math.sqrt

data class Ray(val origin: Tuple, val direction: Tuple) {
    fun position(t: Double): Tuple {
        return origin + direction * t
    }

    fun intersect(other: Sphere): List<Double> {
        val sphereToRay = origin - point(0.0, 0.0, 0.0)
        val a = direction dot direction
        val b = 2 * (direction dot sphereToRay)
        val c = (sphereToRay dot sphereToRay) - 1

        val discriminant = (b * b) - 4 * a * c

        return if (discriminant < 0) {
            emptyList()
        } else {
            listOf(
                (-b - sqrt(discriminant)) / (2 * a),
                (-b + sqrt(discriminant)) / (2 * a)
            )
        }
    }
}