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

    "a gradient pattern linearly interpolates between colors" {
        val pattern = GradientPattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.25, 0.0, 0.0)) shouldBe Color(0.75, 0.75, 0.75)
        pattern.patternAt(point(0.50, 0.0, 0.0)) shouldBe Color(0.5, 0.5, 0.5)
        pattern.patternAt(point(0.75, 0.0, 0.0)) shouldBe Color(0.25, 0.25, 0.25)
    }

    "a ring pattern should extend in both x and z" {
        val pattern = RingPattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(1.0, 0.0, 0.0)) shouldBe Color.BLACK
        pattern.patternAt(point(0.0, 0.0, 1.0)) shouldBe Color.BLACK
        pattern.patternAt(point(0.708, 0.0, 0.708)) shouldBe Color.BLACK
    }

    "Checkers pattern should repeat in x" {
        val pattern = CheckersPattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.99, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(1.01, 0.0, 0.0)) shouldBe Color.BLACK
    }

    "Checkers pattern should repeat in y" {
        val pattern = CheckersPattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 0.99, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 1.01, 0.0)) shouldBe Color.BLACK
    }

    "Checkers pattern should repeat in z" {
        val pattern = CheckersPattern(Color.WHITE, Color.BLACK)
        pattern.patternAt(point(0.0, 0.0, 0.0)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 0.0, 0.99)) shouldBe Color.WHITE
        pattern.patternAt(point(0.0, 0.0, 1.01)) shouldBe Color.BLACK
    }
})