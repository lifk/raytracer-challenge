package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class IntersectionSpec : StringSpec({
    "Initializing a intersection" {
        val s = Sphere()
        val i = Intersection(1.0, s)

        i.t shouldBe 1.0
        i.obj shouldBe s
    }

    "Getting an array of Intersections" {
        val intersections = intersections(Intersection(1.0, Sphere()), Intersection(2.0, Sphere()))
        intersections.size shouldBe 2
    }


    "the hit on all positive intersections" {
        val i1 = Intersection(1.0, Sphere())
        val i2 = Intersection(2.0, Sphere())
        val intersections = intersections(i1, i2)

        intersections.hit() shouldBe i1
    }

    "the hit on some positive intersections" {
        val i1 = Intersection(-1.0, Sphere())
        val i2 = Intersection(1.0, Sphere())
        val intersections = intersections(i1, i2)

        intersections.hit() shouldBe i2
    }

    "the hit on all negative intersections" {
        val i1 = Intersection(-2.0, Sphere())
        val i2 = Intersection(-1.0, Sphere())
        val intersections = intersections(i1, i2)

        intersections.hit() shouldBe null
    }

    "the hit on should be the lowest non negative intersection" {
        val i1 = Intersection(5.0, Sphere())
        val i2 = Intersection(7.0, Sphere())
        val i3 = Intersection(-3.0, Sphere())
        val i4 = Intersection(2.0, Sphere())
        val intersections = intersections(i1, i2, i3, i4)

        intersections.hit() shouldBe i4
    }
})