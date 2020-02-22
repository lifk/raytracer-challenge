package es.lifk.raytracer

import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class WorldSpec : StringSpec({
    "Creating a empty world" {
        val w = World()
        w.objects.size shouldBe 0
        w.light shouldBe null
    }

    "The default world" {
        val w = defaultWorld()
        val s1 = Sphere(
            material = Material(
                color = Color(0.8, 1.0, 0.6),
                diffuse = 0.7,
                specular = 0.2
            )
        )

        val s2 = Sphere(transform = scaling(0.5, 0.5, 0.5))

        w.light shouldBe PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))
        w.objects shouldContainAll listOf(s1, s2)
    }

    "Intersecting the world with a ray" {
        val w = defaultWorld()
        val intersections = w.intersect(Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0)))

        intersections.size shouldBe 4
        intersections[0].t shouldBe 4.0
        intersections[1].t shouldBe 4.5
        intersections[2].t shouldBe 5.5
        intersections[3].t shouldBe 6.0
    }

    "Shading an intersection" {
        val w = defaultWorld()
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))

        val shape = w.objects.first()

        val intersection = Intersection(4.0, shape)
        val comps = intersection.prepareComputations(ray)
        w.shadeHit(comps) shouldBe Color(0.38066, 0.47583, 0.2855)
    }

    "Shading an intersection from the inside" {
        val w = defaultWorld()
        w.light = PointLight(point(0.0, 0.25, 0.0), Color(1.0, 1.0, 1.0))
        val ray = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))

        val shape = w.objects[1]

        val intersection = Intersection(0.5, shape)
        val comps = intersection.prepareComputations(ray)
        w.shadeHit(comps) shouldBe Color(0.90498, 0.90498, 0.90498)
    }

    "The color when a ray misses" {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 1.0, 0.0))
        w.colorAt(r) shouldBe Color(0.0, 0.0, 0.0)
    }

    "The color when a ray hits" {
        val w = defaultWorld()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        w.colorAt(r) shouldBe Color(0.38066, 0.47583, 0.2855)
    }

    "The color with an intersection behind the ray" {
        val w = defaultWorld()
        w.objects[0].material.ambient = 1.0
        w.objects[1].material.ambient = 1.0

        val ray = Ray(point(0.0, 0.0, 0.75), vector(0.0, 0.0, -1.0))
        w.colorAt(ray) shouldBe w.objects[1].material.color
    }
})