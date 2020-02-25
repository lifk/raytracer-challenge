package es.lifk.raytracer

import es.lifk.raytracer.entities.TestPattern
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PatternsSpec : StringSpec({
    "Creating a stripe pattern" {
        val s = StripePattern(Color.WHITE, Color.BLACK)
        s.a shouldBe Color.WHITE
        s.b shouldBe Color.BLACK
    }

    "A stripe pattern is constant in y" {
        val pattern = StripePattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 1.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 2.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 3.0, 0.0)) shouldBe Color.WHITE
    }

    "A stripe pattern is constant in z" {
        val pattern = StripePattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 0.0, 1.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 0.0, 2.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 0.0, 3.0)) shouldBe Color.WHITE
    }

    "A stripe pattern alternates in x" {
        val pattern = StripePattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.9, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(1.0, 0.0, 0.0)) shouldBe Color.BLACK
        pattern.patternAt(point(-0.1, 0.0, 0.0)) shouldBe Color.BLACK
        pattern.patternAt(point(-1.0, 0.0, 0.0)) shouldBe Color.BLACK
        pattern.patternAt(point(-1.1, 0.0, 0.0)) shouldBe Color.WHITE
    }

    "Lighting with a pattern applied" {
        val m = Material(
            ambient = 1.0,
            specular = 0.0,
            diffuse = 0.0,
            pattern = StripePattern(Color.WHITE, Color.BLACK)
        )

        val eyeV = vector(0.0, 0.0, -1.0)
        val normalV = vector(0.0, 0.0, -1.0)
        val pointLight = PointLight(point(0.0, 0.0, -10.0), Color.WHITE)

        lighting(m, Sphere(), pointLight, point(0.9, 0.0, 0.0), eyeV, normalV, false) shouldBe Color.WHITE
        lighting(m, Sphere(), pointLight, point(1.1, 0.0, 0.0), eyeV, normalV, false) shouldBe Color.BLACK
    }

    "Stripes with an object transformation" {
        val obj = Sphere(
            transform = scaling(2.0, 2.0, 2.0),
            material = Material(pattern = StripePattern(Color.WHITE, Color.BLACK))
        )

        obj.material.pattern!!.patternAtShape(obj, point(1.5, 0.0, 0.0)) shouldBe Color.WHITE
    }

    "Stripes with an pattern transformation" {
        val obj = Sphere(
            material = Material(pattern = StripePattern(Color.WHITE, Color.BLACK, scaling(2.0, 2.0, 2.0)))
        )

        obj.material.pattern!!.patternAtShape(obj, point(1.5, 0.0, 0.0)) shouldBe Color.WHITE
    }

    "Stripes with an pattern and object transformation" {
        val obj = Sphere(
            transform = scaling(2.0, 2.0, 2.0),
            material = Material(pattern = StripePattern(Color.WHITE, Color.BLACK, translation(0.5, 0.0, 0.0)))
        )

        obj.material.pattern!!.patternAtShape(obj, point(2.5, 0.0, 0.0)) shouldBe Color.WHITE
    }

    "test pattern with an object transformation" {
        val obj = Sphere(
            transform = scaling(2.0, 2.0, 2.0),
            material = Material(pattern = TestPattern())
        )

        obj.material.pattern!!.patternAtShape(obj, point(2.0, 3.0, 4.0)) shouldBe Color(1.0, 1.5, 2.0)
    }

    "test pattern with an pattern transformation" {
        val obj = Sphere(
            material = Material(pattern = TestPattern(scaling(2.0, 2.0, 2.0)))
        )

        obj.material.pattern!!.patternAtShape(obj, point(2.0, 3.0, 4.0)) shouldBe Color(1.0, 1.5, 2.0)
    }

    "test pattern with an pattern and object transformation" {
        val obj = Sphere(
            transform = scaling(2.0, 2.0, 2.0),
            material = Material(pattern = TestPattern(translation(0.5, 1.0, 1.5)))
        )

        obj.material.pattern!!.patternAtShape(obj, point(2.5, 3.0, 3.5)) shouldBe Color(0.75, 0.5, 0.25)
    }
})