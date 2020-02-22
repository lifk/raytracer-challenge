package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.PI
import kotlin.math.sqrt

class MatrixTransformationsSpec : StringSpec({
    "Multiplying by a es.lifk.raytracer.translation matrix" {
        val transform = translation(5.0, -3.0, 2.0)
        val point = point(-3.0, 4.0, 5.0)

        transform * point shouldBe point(2.0, 1.0, 7.0)
    }

    "Multiplying by the inverse of a es.lifk.raytracer.translation matrix" {
        val transform = translation(5.0, -3.0, 2.0)
        val point = point(-3.0, 4.0, 5.0)

        transform.inverse() * point shouldBe point(-8.0, 7.0, 3.0)
    }

    "es.lifk.raytracer.translation doesn't affect vectors" {
        val transform = translation(5.0, -3.0, 2.0)
        val vector = vector(-3.0, 4.0, 5.0)

        transform * vector shouldBe vector
    }


    "Scaling matrix applied to a es.lifk.raytracer.point" {
        val transform = scaling(2.0, 3.0, 4.0)
        val point = point(-4.0, 6.0, 8.0)

        transform * point shouldBe point(-8.0, 18.0, 32.0)
    }

    "Scaling matrix applied to a es.lifk.raytracer.vector" {
        val transform = scaling(2.0, 3.0, 4.0)
        val point = vector(-4.0, 6.0, 8.0)

        transform * point shouldBe vector(-8.0, 18.0, 32.0)
    }

    "Multiplying by the inverse of a es.lifk.raytracer.scaling matrix" {
        val transform = scaling(2.0, 3.0, 4.0)
        val point = vector(-4.0, 6.0, 8.0)

        transform.inverse() * point shouldBe vector(-2.0, 2.0, 2.0)
    }

    "reflection is es.lifk.raytracer.scaling by a negative number" {
        val transform = scaling(-1.0, 1.0, 1.0)
        val point = point(2.0, 3.0, 4.0)

        transform * point shouldBe point(-2.0, 3.0, 4.0)
    }

    "Rotating a es.lifk.raytracer.point around the x axis" {
        val point = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(PI / 4)
        val fullQuarter = rotationX(PI / 2)

        halfQuarter * point shouldBe point(0.0, sqrt(2.0) / 2.0, sqrt(2.0) / 2.0)
        fullQuarter * point shouldBe point(0.0, 0.0, 1.0)
    }

    "Rotating a es.lifk.raytracer.point around the x axis inversed" {
        val point = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(PI / 4)

        halfQuarter.inverse() * point shouldBe point(
            0.0,
            sqrt(2.0) / 2.0,
            -(sqrt(2.0) / 2.0)
        )
    }

    "Rotating a es.lifk.raytracer.point around the Y axis" {
        val point = point(0.0, 0.0, 1.0)
        val halfQuarter = rotationY(PI / 4)
        val fullQuarter = rotationY(PI / 2)

        halfQuarter * point shouldBe point(sqrt(2.0) / 2.0, 0.0, sqrt(2.0) / 2.0)
        fullQuarter * point shouldBe point(1.0, 0.0, 0.0)
    }

    "Rotating a es.lifk.raytracer.point around the Z axis" {
        val point = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationZ(PI / 4)
        val fullQuarter = rotationZ(PI / 2)

        halfQuarter * point shouldBe point(
            -(sqrt(2.0) / 2.0),
            sqrt(2.0) / 2.0,
            0.0
        )
        fullQuarter * point shouldBe point(-1.0, 0.0, 0.0)
    }

    "Shearing moves x in proportion to y" {
        val point = point(2.0, 3.0, 4.0)
        val transform = shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        transform * point shouldBe point(5.0, 3.0, 4.0)
    }

    "Shearing moves x in proportion to z" {
        val point = point(2.0, 3.0, 4.0)
        val transform = shearing(1.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        transform * point shouldBe point(5.0, 3.0, 4.0)
    }

    "Shearing moves y in proportion to x" {
        val point = point(2.0, 3.0, 4.0)
        val transform = shearing(0.0, 0.0, 1.0, 0.0, 0.0, 0.0)

        transform * point shouldBe point(2.0, 5.0, 4.0)
    }

    "Shearing moves y in proportion to z" {
        val point = point(2.0, 3.0, 4.0)
        val transform = shearing(0.0, 0.0, 0.0, 1.0, 0.0, 0.0)

        transform * point shouldBe point(2.0, 7.0, 4.0)
    }

    "Shearing moves z in proportion to x" {
        val point = point(2.0, 3.0, 4.0)
        val transform = shearing(0.0, 0.0, 0.0, 0.0, 1.0, 0.0)

        transform * point shouldBe point(2.0, 3.0, 6.0)
    }

    "Shearing moves z in proportion to y" {
        val point = point(2.0, 3.0, 4.0)
        val transform = shearing(0.0, 0.0, 0.0, 0.0, 0.0, 1.0)

        transform * point shouldBe point(2.0, 3.0, 7.0)
    }

    "Chained transformations must be applied in reverse order" {
        val point = point(1.0, 0.0, 1.0)
        val r = rotationX(PI / 2)
        val s = scaling(5.0, 5.0, 5.0)
        val t = translation(10.0, 5.0, 7.0)

        (t * s * r) * point shouldBe point(15.0, 0.0, 7.0)
        point.transform(r, s, t) shouldBe point(15.0, 0.0, 7.0)
    }

    "The transformation matrix for the default orientation" {
        val matrix = viewTransform(
            point(0.0, 0.0, 0.0),
            point(0.0, 0.0, -1.0),
            vector(0.0, 1.0, 0.0)
        )

        matrix shouldBe IDENTITY_MATRIX
    }

    "The transformation matrix looking in positive z direction" {
        val matrix = viewTransform(
            point(0.0, 0.0, 0.0),
            point(0.0, 0.0, 1.0),
            vector(0.0, 1.0, 0.0)
        )

        matrix shouldBe scaling(-1.0, 1.0, -1.0)
    }

    "The transformation matrix moves around the world" {
        val matrix = viewTransform(
            point(0.0, 0.0, 8.0),
            point(0.0, 0.0, 0.0),
            vector(0.0, 1.0, 0.0)
        )

        matrix shouldBe translation(0.0, 0.0, -8.0)
    }

    "An arbitrary view transformation" {
        val matrix = viewTransform(
            point(1.0, 3.0, 2.0),
            point(4.0, -2.0, 8.0),
            vector(1.0, 1.0, 0.0)
        )

        matrix shouldBe Matrix(
            A(-0.50709, 0.50709, 0.67612, -2.36643),
            A(0.76772, 0.60609, 0.12122, -2.82843),
            A(-0.35857, 0.59761, -0.71714, 0.00000),
            A(0.00000, 0.00000, 0.00000, 1.00000)
        )
    }
})
