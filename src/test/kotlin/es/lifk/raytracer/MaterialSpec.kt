package es.lifk.raytracer

import io.kotlintest.matchers.doubles.shouldBeGreaterThan
import io.kotlintest.matchers.doubles.shouldBeLessThan
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class MaterialSpec : StringSpec({
    "Default reflection is 0" {
        val m = Material()
        m.reflective shouldBe 0.0
    }

    "Transparency and Refractive Index for the default material" {
        val m = Material()
        m.transparency shouldBe 0.0
        m.refractiveIndex shouldBe 1.0
    }

    "A helper method to produce a glassy sphere" {
        val glassSphere = glassSphere()
        glassSphere.transform shouldBe IDENTITY_MATRIX
        glassSphere.material.transparency shouldBe 1.0
        glassSphere.material.refractiveIndex shouldBe 1.5
    }

    "Finding n1 and n2 at various intersections" {
        val a = glassSphere().copy(transform = scaling(2.0, 2.0, 2.0))
        val b = glassSphere().copy(
            transform = translation(0.0, 0.0, 0.25),
            material = Material(transparency = 1.0, refractiveIndex = 2.0)
        )
        val c = glassSphere().copy(
            transform = translation(0.0, 0.0, 0.25),
            material = Material(transparency = 1.0, refractiveIndex = 2.5)
        )

        val r = Ray(point(0.0, 0.0, -4.0), vector(0.0, 0.0, 1.0))

        val intersections = listOf(
            Triple(Intersection(2.00, a), 1.0, 1.5),
            Triple(Intersection(2.75, b), 1.5, 2.0),
            Triple(Intersection(3.25, c), 2.0, 2.5),
            Triple(Intersection(4.75, b), 2.5, 2.5),
            Triple(Intersection(5.25, c), 2.5, 1.5),
            Triple(Intersection(6.00, a), 1.5, 1.0)
        )

        intersections.forEach {
            val comps = it.first.prepareComputations(r, intersections.map { it.first })
            comps.n1 shouldBe it.second
            comps.n2 shouldBe it.third
        }
    }

    "The underpoint is offset below the surface" {
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = glassSphere().copy(translation(0.0, 0.0, 1.0))
        val intersection = Intersection(5.0, shape)

        val comps = intersection.prepareComputations(r, listOf(intersection))
        comps.underPoint.z.shouldBeGreaterThan(EPSILON / 2.0)
        comps.point.z.shouldBeLessThan(comps.underPoint.z)
    }
})

fun glassSphere(): Sphere {
    return Sphere(material = Material(transparency = 1.0, refractiveIndex = 1.5))
}