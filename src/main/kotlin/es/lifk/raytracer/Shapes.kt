package es.lifk.raytracer

import kotlin.math.abs
import kotlin.math.sqrt

interface Shape {
    var transform: Matrix
    val material: Material

    fun normalAt(point: Tuple): Tuple {
        val objectPoint = transform.inverse() * point
        val objectNormal = localNormalAt(objectPoint)
        val worldNormal = transform.inverse().transpose() * objectNormal

        return worldNormal.copy(w = 0.0).normalize()
    }

    fun localNormalAt(point: Tuple): Tuple
    fun localIntersect(ray: Ray): Array<Intersection>
}

data class Sphere(override var transform: Matrix = IDENTITY_MATRIX, override val material: Material = Material()) :
    Shape {
    override fun localNormalAt(point: Tuple): Tuple {
        return point - point(0.0, 0.0, 0.0)
    }

    override fun localIntersect(ray: Ray): Array<Intersection> {
        val sphereToRay = ray.origin - point(0.0, 0.0, 0.0)
        val a = ray.direction dot ray.direction
        val b = 2 * (ray.direction dot sphereToRay)
        val c = (sphereToRay dot sphereToRay) - 1

        val discriminant = (b * b) - 4 * a * c

        return if (discriminant < 0) {
            emptyArray()
        } else {
            intersections(
                Intersection((-b - sqrt(discriminant)) / (2 * a), this),
                Intersection((-b + sqrt(discriminant)) / (2 * a), this)
            )
        }
    }
}

data class Plane(override var transform: Matrix = IDENTITY_MATRIX, override val material: Material = Material()) :
    Shape {
    override fun localNormalAt(point: Tuple): Tuple {
        return vector(0.0, 1.0, 0.0)
    }

    override fun localIntersect(ray: Ray): Array<Intersection> {
        if (abs(ray.direction.y) < EPSILON) return emptyArray()

        return arrayOf(Intersection(-ray.origin.y / ray.direction.y, this))
    }
}
