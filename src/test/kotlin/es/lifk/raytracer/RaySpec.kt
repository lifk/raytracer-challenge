package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class RaySpec : StringSpec({
    "Initializing a ray" {
        val origin = point(1.0, 2.0, 3.0)
        val direction = vector(4.0, 5.0, 6.0)

        val ray = Ray(origin, direction)

        ray.origin shouldBe origin
        ray.direction shouldBe direction
    }

    "computing a point from a distance" {
        val ray = Ray(point(2.0, 3.0, 4.0), vector(1.0, 0.0, 0.0))
        ray.position(0.0) shouldBe point(2.0, 3.0, 4.0)
        ray.position(1.0) shouldBe point(3.0, 3.0, 4.0)
        ray.position(-1.0) shouldBe point(1.0, 3.0, 4.0)
        ray.position(2.5) shouldBe point(4.5, 3.0, 4.0)
    }

    "A ray intersects a sphere in two points" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = ray.intersect(sphere)
        xs.size shouldBe 2
        xs[0].t shouldBe 4.0
        xs[0].obj shouldBe sphere
        xs[1].t shouldBe 6.0
        xs[1].obj shouldBe sphere
    }

    "A ray intersects a sphere in one point" {
        val ray = Ray(point(0.0, 1.0, -5.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = ray.intersect(sphere)
        xs.size shouldBe 2
        xs[0].t shouldBe 5.0
        xs[1].t shouldBe 5.0
    }


    "A ray misses a sphere" {
        val ray = Ray(point(0.0, 2.0, -5.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = ray.intersect(sphere)
        xs.size shouldBe 0
    }

    "A ray starts inside a sphere" {
        val ray = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = ray.intersect(sphere)
        xs.size shouldBe 2
        xs[0].t shouldBe -1.0
        xs[1].t shouldBe 1.0
    }

    "A sphere is behind the ray" {
        val ray = Ray(point(0.0, 0.0, 5.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()

        val xs = ray.intersect(sphere)
        xs.size shouldBe 2
        xs[0].t shouldBe -6.0
        xs[1].t shouldBe -4.0
    }

    "translating a ray" {
        val ray = Ray(point(1.0, 2.0, 3.0), vector(0.0, 1.0, 0.0))
        val newRay = ray.transform(translation(3.0, 4.0, 5.0))
        newRay.origin shouldBe point(4.0, 6.0, 8.0)
        newRay.direction shouldBe vector(0.0, 1.0, 0.0)
    }

    "Scaling a ray" {
        val ray = Ray(point(1.0, 2.0, 3.0), vector(0.0, 1.0, 0.0))
        val newRay = ray.transform(scaling(2.0, 3.0, 4.0))
        newRay.origin shouldBe point(2.0, 6.0, 12.0)
        newRay.direction shouldBe vector(0.0, 3.0, 0.0)
    }

    "Intersected a scaled sphere with a ray" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()
        sphere.transform = scaling(2.0, 2.0, 2.0)

        val intersections = ray.intersect(sphere)
        intersections.size shouldBe 2
        intersections[0].t shouldBe 3.0
        intersections[1].t shouldBe 7.0
    }

    "Intersected a translated sphere with a ray" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val sphere = Sphere()
        sphere.transform = translation(5.0, 0.0, 0.0)

        val intersections = ray.intersect(sphere)
        intersections.size shouldBe 0
    }
})

