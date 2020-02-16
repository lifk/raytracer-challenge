import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.PI
import kotlin.math.sqrt

class MatrixTransformationsSpec : StringSpec({
    "Multiplying by a translation matrix" {
        val transform = translation(5.0, -3.0, 2.0)
        val point = point(-3.0, 4.0, 5.0)

        transform * point shouldBe point(2.0, 1.0, 7.0)
    }

    "Multiplying by the inverse of a translation matrix" {
        val transform = translation(5.0, -3.0, 2.0)
        val point = point(-3.0, 4.0, 5.0)

        transform.inverse() * point shouldBe point(-8.0, 7.0, 3.0)
    }

    "translation doesn't affect vectors" {
        val transform = translation(5.0, -3.0, 2.0)
        val vector = vector(-3.0, 4.0, 5.0)

        transform * vector shouldBe vector
    }


    "Scaling matrix applied to a point" {
        val transform = scaling(2.0, 3.0, 4.0)
        val point = point(-4.0, 6.0, 8.0)

        transform * point shouldBe point(-8.0, 18.0, 32.0)
    }

    "Scaling matrix applied to a vector" {
        val transform = scaling(2.0, 3.0, 4.0)
        val point = vector(-4.0, 6.0, 8.0)

        transform * point shouldBe vector(-8.0, 18.0, 32.0)
    }

    "Multiplying by the inverse of a scaling matrix" {
        val transform = scaling(2.0, 3.0, 4.0)
        val point = vector(-4.0, 6.0, 8.0)

        transform.inverse() * point shouldBe vector(-2.0, 2.0, 2.0)
    }

    "reflection is scaling by a negative number" {
        val transform = scaling(-1.0, 1.0, 1.0)
        val point = point(2.0, 3.0, 4.0)

        transform * point shouldBe point(-2.0, 3.0, 4.0)
    }

    "Rotating a point around the x axis" {
        val point = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(PI / 4)
        val fullQuarter = rotationX(PI / 2)

        halfQuarter * point shouldBe point(0.0, sqrt(2.0) / 2.0, sqrt(2.0) / 2.0)
        fullQuarter * point shouldBe point(0.0, 0.0, 1.0)
    }

    "Rotating a point around the x axis inversed" {
        val point = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationX(PI / 4)

        halfQuarter.inverse() * point shouldBe point(0.0, sqrt(2.0) / 2.0, -(sqrt(2.0) / 2.0))
    }

    "Rotating a point around the Y axis" {
        val point = point(0.0, 0.0, 1.0)
        val halfQuarter = rotationY(PI / 4)
        val fullQuarter = rotationY(PI / 2)

        halfQuarter * point shouldBe point(sqrt(2.0) / 2.0, 0.0, sqrt(2.0) / 2.0)
        fullQuarter * point shouldBe point(1.0, 0.0, 0.0)
    }

    "Rotating a point around the Z axis" {
        val point = point(0.0, 1.0, 0.0)
        val halfQuarter = rotationZ(PI / 4)
        val fullQuarter = rotationZ(PI / 2)

        halfQuarter * point shouldBe point(-(sqrt(2.0) / 2.0), sqrt(2.0) / 2.0, 0.0)
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
})
