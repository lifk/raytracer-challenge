package es.lifk.raytracer

import kotlin.math.sqrt

data class Ray(val origin: Tuple, val direction: Tuple) {
    fun position(t: Double): Tuple {
        return origin + direction * t
    }

    fun intersect(other: Sphere): Array<Intersection> {
        val ray = transform(other.transform.inverse())
        val sphereToRay = ray.origin - point(0.0, 0.0, 0.0)
        val a = ray.direction dot ray.direction
        val b = 2 * (ray.direction dot sphereToRay)
        val c = (sphereToRay dot sphereToRay) - 1

        val discriminant = (b * b) - 4 * a * c

        return if (discriminant < 0) {
            emptyArray()
        } else {
            intersections(
                Intersection((-b - sqrt(discriminant)) / (2 * a), other),
                Intersection((-b + sqrt(discriminant)) / (2 * a), other)
            )
        }
    }

    fun transform(matrix: Matrix): Ray {
        return Ray(matrix * origin, matrix * direction)
    }
}