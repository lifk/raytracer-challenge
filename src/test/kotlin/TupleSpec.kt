import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.sqrt

class TupleSpec : StringSpec({
    "point is tuple with w = 1" {
        point(4.0, -4.0, 3.2).w shouldBe 1.0
    }

    "vector is tuple with w = 0" {
        vector(4.0, -4.0, 3.2).w shouldBe 0.0
    }

    "test equality" {
        vector(0.267261, 0.534522, 0.801784).normalize() shouldBe vector(0.267261, 0.534522, 0.801784001)
    }

    "adding two tuples" {
        Tuple(3.0, -2.0, 5.0, 1.0) + Tuple(-2.0, 3.0, 1.0, 0.0) shouldBe Tuple(1.0, 1.0, 6.0, 1.0)
    }

    "Subtracting two points" {
        point(3.0, 2.0, 1.0) - point(5.0, 6.0, 7.0) shouldBe vector(-2.0, -4.0, -6.0)
    }

    "Subtracting a vector from a point" {
        point(3.0, 2.0, 1.0) - vector(5.0, 6.0, 7.0) shouldBe point(-2.0, -4.0, -6.0)
    }

    "Subtracting two vectors" {
        vector(3.0, 2.0, 1.0) - vector(5.0, 6.0, 7.0) shouldBe vector(-2.0, -4.0, -6.0)
    }

    "negating a tuple" {
        -Tuple(1.0, -2.0, 3.0, -4.0) shouldBe Tuple(-1.0, 2.0, -3.0, 4.0)
    }

    "multiplying a tuple by a scalar" {
        Tuple(1.0, -2.0, 3.0, -4.0) * 3.5 shouldBe Tuple(3.5, -7.0, 10.5, -14.0)
    }

    "multiplying a tuple by a fraction" {
        Tuple(1.0, -2.0, 3.0, -4.0) * 0.5 shouldBe Tuple(0.5, -1.0, 1.5, -2.0)
    }
    "dividing a tuple by a scalar" {
        Tuple(1.0, -2.0, 3.0, -4.0) / 2.0 shouldBe Tuple(0.5, -1.0, 1.5, -2.0)
    }

    "computing the magnitude of vector" {
        vector(1.0, 0.0, 0.0).magnitude() shouldBe 1.0
        vector(0.0, 1.0, 0.0).magnitude() shouldBe 1.0
        vector(0.0, 0.0, 1.0).magnitude() shouldBe 1.0
        vector(1.0, 2.0, 3.0).magnitude() shouldBe sqrt(14.0)
        vector(-1.0, -2.0, -3.0).magnitude() shouldBe sqrt(14.0)
    }

    "Normalizing vectors" {
        vector(4.0, 0.0, 0.0).normalize() shouldBe vector(1.0, 0.0, 0.0)
        vector(1.0, 2.0, 3.0).normalize() shouldBe vector(0.267261, 0.534522, 0.801784)
    }

    "dot product" {
        vector(1.0, 2.0, 3.0) dot vector(2.0, 3.0, 4.0) shouldBe 20.0
    }

    "cross product " {
        vector(1.0, 2.0, 3.0) cross vector(2.0, 3.0, 4.0) shouldBe vector(-1.0, 2.0, -1.0)
        vector(2.0, 3.0, 4.0) cross vector(1.0, 2.0, 3.0) shouldBe vector(1.0, -2.0, 1.0)
    }

    "color" {
        Color(-0.5, 0.4, 1.7).red shouldBe -0.5
        Color(0.9, 0.6, 0.75) + Color(0.7, 0.1, 0.25) shouldBe Color(1.6, 0.7, 1.0)
        Color(0.9, 0.6, 0.75) - Color(0.7, 0.1, 0.25) shouldBe Color(0.2, 0.5, 0.5)
        Color(0.2, 0.3, 0.4) * 2.0 shouldBe Color(0.4, 0.6, 0.8)
    }
})