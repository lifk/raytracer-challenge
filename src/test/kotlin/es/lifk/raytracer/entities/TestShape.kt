package es.lifk.raytracer.entities

import es.lifk.raytracer.*

data class TestShape(override var transform: Matrix = IDENTITY_MATRIX, override val material: Material = Material()) :
    Shape {
    lateinit var testRay: Ray

    override fun localNormalAt(point: Tuple): Tuple {
        return vector(point.x, point.y, point.z)
    }

    override fun localIntersect(ray: Ray): Array<Intersection> {
        testRay = ray
        return emptyArray()
    }
}