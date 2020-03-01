package es.lifk.raytracer

import es.lifk.raytracer.entities.TestPattern
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.sqrt

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

    "There is no shadow when nothing is collinear with point and light" {
        val w = defaultWorld()
        val p = point(0.0, 10.0, 0.0)
        w.isShadowed(p) shouldBe false
    }


    "There is shadow when an object is collinear with point and light" {
        val w = defaultWorld()
        val p = point(10.0, -10.0, 10.0)
        w.isShadowed(p) shouldBe true
    }


    "There is no shadow when an object is behind the light" {
        val w = defaultWorld()
        val p = point(-20.0, 20.0, -20.0)
        w.isShadowed(p) shouldBe false
    }

    "There is no shadow when an object is behind the point" {
        val w = defaultWorld()
        val p = point(-2.0, 20.0, -2.0)
        w.isShadowed(p) shouldBe false
    }

    "ShadeHit is given an intersection in shadow" {
        val w = World(PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0)))
        w.objects.add(Sphere())
        val sphere = Sphere(transform = translation(0.0, 0.0, 10.0))
        w.objects.add(sphere)

        val r = Ray(point(0.0, 0.0, 5.0), vector(0.0, 0.0, 1.0))
        val i = Intersection(4.0, sphere)
        val comps = i.prepareComputations(r)
        w.shadeHit(comps) shouldBe Color(0.1, 0.1, 0.1)
    }

    "the hit should offset the point" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val shape = Sphere(transform = translation(0.0, 0.0, 1.0))
        val i = Intersection(5.0, shape)
        val comps = i.prepareComputations(ray)
        comps.overPoint.z < -EPSILON / 2
    }

    "The reflected color for a non reflective material" {
        val world = defaultWorld()
        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 0.0, 1.0))
        val shape = world.objects[1]
        shape.material.ambient = 1.0
        val i = Intersection(1.0, shape)
        val comps = i.prepareComputations(r)
        world.reflectedColor(comps) shouldBe Color.BLACK
    }

    "The color for a reflective material" {
        val world = defaultWorld()
        val shape = Plane(material = Material(reflective = 0.5), transform = translation(0.0, -1.0, 0.0))
        world.objects.add(shape)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)
        world.reflectedColor(comps) shouldBe Color(0.19033, 0.23791, 0.14274)
    }

    "Shade hit with a reflective material" {
        val world = defaultWorld()
        val shape = Plane(material = Material(reflective = 0.5), transform = translation(0.0, -1.0, 0.0))
        world.objects.add(shape)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)
        world.shadeHit(comps) shouldBe Color(0.87675, 0.92434, 0.82917)
    }

    "Color at with mutually reflective surfaces" {
        val w = World(light = PointLight(point(0.0, 0.0, 0.0), Color.WHITE))
        val lowerPlane = Plane(material = Material(reflective = 1.0), transform = translation(0.0, -1.0, 0.0))
        val upperPlane = Plane(material = Material(reflective = 1.0), transform = translation(0.0, 1.0, 0.0))

        w.objects.add(lowerPlane)
        w.objects.add(upperPlane)

        val r = Ray(point(0.0, 0.0, 0.0), vector(0.0, 1.0, 0.0))
        w.colorAt(r).shouldBeTypeOf<Color>()
    }

    "The reflected color at the maximum recursive depth" {
        val world = defaultWorld()
        val shape = Plane(material = Material(reflective = 0.5), transform = translation(0.0, -1.0, 0.0))
        world.objects.add(shape)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))
        val i = Intersection(sqrt(2.0), shape)
        val comps = i.prepareComputations(r)
        world.reflectedColor(comps, 0) shouldBe Color.BLACK
    }

    "the refracted color with an opaque surface" {
        val w = defaultWorld()
        val shape = w.objects.first()
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val intersections = listOf(Intersection(4.0, shape), Intersection(6.0, shape))
        val comps = intersections.first().prepareComputations(r, intersections)

        w.refractedColor(comps, 5) shouldBe Color.BLACK
    }

    "the refracted color at the maximum recursive depth" {
        val w = defaultWorld()
        val shape = w.objects.first()
        shape.material.refractiveIndex = 1.5
        shape.material.transparency = 1.0
        val r = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val intersections = listOf(Intersection(4.0, shape), Intersection(6.0, shape))
        val comps = intersections.first().prepareComputations(r, intersections)

        w.refractedColor(comps, 0) shouldBe Color.BLACK
    }

    "the refracted color under total internal reflection" {
        val w = defaultWorld()
        val shape = w.objects.first()
        shape.material.refractiveIndex = 1.5
        shape.material.transparency = 1.0
        val r = Ray(point(0.0, 0.0, sqrt(2.0) / 2.0), vector(0.0, 1.0, 0.0))
        val intersections = listOf(Intersection(-sqrt(2.0) / 2.0, shape), Intersection(sqrt(2.0) / 2.0, shape))
        val comps = intersections[1].prepareComputations(r, intersections)

        w.refractedColor(comps, 0) shouldBe Color.BLACK
    }

    "the refracted color with a reflected ray" {
        val w = defaultWorld()
        val a = w.objects.first()
        a.material.ambient = 1.0
        a.material.pattern = TestPattern()

        val b = w.objects[1]
        b.material.transparency = 1.0
        b.material.refractiveIndex = 1.5

        val r = Ray(point(0.0, 0.0, 0.1), vector(0.0, 1.0, 0.0))
        val intersections = listOf(
            Intersection(-0.9899, a),
            Intersection(-0.4899, b),
            Intersection(0.4899, b),
            Intersection(0.9899, a)
        )

        val comps = intersections[2].prepareComputations(r, intersections)

        w.refractedColor(comps, 5) shouldBe Color(0.0, 0.99888, 0.04721)
    }

    "shadeHit with a transparent material" {
        val w = defaultWorld()
        val floor = Plane(
            transform = translation(0.0, -1.0, 0.0),
            material = Material(transparency = 0.5, refractiveIndex = 1.5)
        )
        val ball = Sphere(translation(0.0, -3.5, -0.5), Material(color = Color(1.0, 0.0, 0.0), ambient = 0.5))

        w.objects.add(floor)
        w.objects.add(ball)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))

        val intersections = listOf(
            Intersection(sqrt(2.0), floor)
        )

        val comps = intersections[0].prepareComputations(r, intersections)

        w.shadeHit(comps, 5) shouldBe Color(0.93642, 0.68642, 0.68642)
    }

    "shadeHit with a reflective, transparent material" {
        val w = defaultWorld()
        val floor = Plane(
            transform = translation(0.0, -1.0, 0.0),
            material = Material(transparency = 0.5, refractiveIndex = 1.5, reflective = 0.5)
        )
        val ball = Sphere(translation(0.0, -3.5, -0.5), Material(color = Color(1.0, 0.0, 0.0), ambient = 0.5))

        w.objects.add(floor)
        w.objects.add(ball)

        val r = Ray(point(0.0, 0.0, -3.0), vector(0.0, -sqrt(2.0) / 2.0, sqrt(2.0) / 2.0))

        val intersections = listOf(
            Intersection(sqrt(2.0), floor)
        )

        val comps = intersections[0].prepareComputations(r, intersections)

        w.shadeHit(comps, 5) shouldBe Color(0.93391, 0.69643, 0.69243)
    }
})