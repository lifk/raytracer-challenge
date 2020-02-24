package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PlaneSpec : StringSpec({
    "The normal of a plane is constant everywhere" {
        val p = Plane()
        p.localNormalAt(point(0.0, 0.0, 0.0)) shouldBe vector(0.0, 1.0, 0.0)
        p.localNormalAt(point(10.0, 0.0, -10.0)) shouldBe vector(0.0, 1.0, 0.0)
        p.localNormalAt(point(-5.0, 0.0, 150.0)) shouldBe vector(0.0, 1.0, 0.0)
    }

    "Intersect with a ray parallel to the plane" {
        val p = Plane()
        val r = Ray(point(0.0, 10.0, 0.0), vector(0.0, 0.0, 1.0))
        p.localIntersect(r).size shouldBe 0
    }

    "Intersect with a coplanar ray" {
        val p = Plane()
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        p.localIntersect(r).size shouldBe 0
    }

    "A ray intersecting a plane from above" {
        val p = Plane()
        val r = Ray(point(0.0, 1.0, 0.0), vector(0.0, -1.0, 0.0))
        val intersections = p.localIntersect(r)
        intersections.size shouldBe 1
        intersections[0].t shouldBe 1.0
        intersections[0].obj shouldBe p
    }

    "A ray intersecting a plane from below" {
        val p = Plane()
        val r = Ray(point(0.0, -1.0, 0.0), vector(0.0, 1.0, 0.0))
        val intersections = p.localIntersect(r)
        intersections.size shouldBe 1
        intersections[0].t shouldBe 1.0
        intersections[0].obj shouldBe p
    }
})