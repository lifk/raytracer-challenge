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

    "Precomputing the state of an intersection" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere()
        val i = Intersection(4.0, shape)
        val comps = i.prepareComputations(ray)
        comps.t shouldBe i.t
        comps.obj shouldBe i.obj
        comps.point shouldBe point(0.0, 0.0, -1.0)
        comps.eyeV shouldBe vector(0.0, 0.0, -1.0)
        comps.normalV shouldBe vector(0.0, 0.0, -1.0)
    }

    "the hit when a intersection occurs on the inside" {
        val ray = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere()
        val i = Intersection(1.0, shape)
        val comps = i.prepareComputations(ray)
        comps.point shouldBe point(0.0, 0.0, 1.0)
        comps.eyeV shouldBe vector(0.0, 0.0, -1.0)
        comps.inside shouldBe true
        comps.normalV shouldBe vector(0.0, 0.0, -1.0)
    }
})