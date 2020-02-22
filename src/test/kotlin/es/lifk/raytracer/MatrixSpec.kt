package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class MatrixSpec : StringSpec({
    "Matrix initialization" {
        val matrix = Matrix(
            A(1.0, 2.0, 3.0, 4.0),
            A(5.5, 6.5, 7.5, 8.5),
            A(9.0, 10.0, 11.0, 12.0),
            A(13.5, 14.5, 15.5, 16.5)
        )

        matrix[0, 0] shouldBe 1.0
        matrix[0, 3] shouldBe 4.0
        matrix[1, 0] shouldBe 5.5
        matrix[1, 2] shouldBe 7.5
        matrix[2, 2] shouldBe 11.0
        matrix[3, 0] shouldBe 13.5
        matrix[3, 2] shouldBe 15.5

        val matrix2 = Matrix(
            A(-3.0, 5.0),
            A(1.0, -2.0)
        )

        matrix2[0, 0] shouldBe -3.0
        matrix2[1, 1] shouldBe -2.0

        val matrix3 = Matrix(
            A(-3.0, 5.0, 0.0),
            A(1.0, -2.0, -7.0),
            A(0.0, 1.0, 1.0)
        )

        matrix3[0, 0] shouldBe -3.0
        matrix3[1, 1] shouldBe -2.0
        matrix3[2, 2] shouldBe 1.0
    }

    "Matrix equality" {
        val matrix = Matrix(
            A(1.0, 2.0, 3.0, 4.0),
            A(5.5, 6.5, 7.5, 8.5),
            A(9.0, 10.0, 11.0, 12.0),
            A(13.5, 14.5, 15.5, 16.5)
        )

        val matrix2 = Matrix(
            A(1.0, 2.0, 3.0, 4.0),
            A(5.5, 6.5, 7.5, 8.5),
            A(9.0, 10.0, 11.0, 12.0),
            A(13.5, 14.5, 15.5, 16.5)
        )

        (matrix == matrix2) shouldBe true

        val matrix3 = Matrix(
            A(1.0, 2.0, 3.0, 4.0),
            A(5.5, 6.5, 7.5, 8.5),
            A(9.0, 10.0, 11.0, 12.0),
            A(13.5, 14.5, 15.5, 16.4)
        )

        (matrix == matrix3) shouldBe false
    }


    "Matrix multiplication" {
        val matrix = Matrix(
            A(1.0, 2.0, 3.0, 4.0),
            A(5.0, 6.0, 7.0, 8.0),
            A(9.0, 8.0, 7.0, 6.0),
            A(5.0, 4.0, 3.0, 2.0)
        )

        val matrix2 = Matrix(
            A(-2.0, 1.0, 2.0, 3.0),
            A(3.0, 2.0, 1.0, -1.0),
            A(4.0, 3.0, 6.0, 5.0),
            A(1.0, 2.0, 7.0, 8.0)
        )

        matrix * matrix2 shouldBe Matrix(
            A(20.0, 22.0, 50.0, 48.0),
            A(44.0, 54.0, 114.0, 108.0),
            A(40.0, 58.0, 110.0, 102.0),
            A(16.0, 26.0, 46.0, 42.0)
        )
    }

    "Matrix by tuple multiplication" {
        val matrix = Matrix(
            A(1.0, 2.0, 3.0, 4.0),
            A(2.0, 4.0, 4.0, 2.0),
            A(8.0, 6.0, 4.0, 1.0),
            A(0.0, 0.0, 0.0, 1.0)
        )

        val tuple = Tuple(1.0, 2.0, 3.0, 1.0)

        matrix * tuple shouldBe Tuple(18.0, 24.0, 33.0, 1.0)
    }

    "Matrix identity multiplication" {
        val matrix = Matrix(
            A(0.0, 1.0, 2.0, 4.0),
            A(1.0, 2.0, 4.0, 8.0),
            A(2.0, 4.0, 8.0, 16.0),
            A(4.0, 8.0, 16.0, 32.0)
        )

        matrix * IDENTITY_MATRIX shouldBe matrix
    }

    "Transposing a matrix" {
        val matrix = Matrix(
            A(0.0, 9.0, 3.0, 0.0),
            A(9.0, 8.0, 0.0, 8.0),
            A(1.0, 8.0, 5.0, 3.0),
            A(0.0, 0.0, 5.0, 8.0)
        )

        val matrix2 = Matrix(
            A(0.0, 9.0, 1.0, 0.0),
            A(9.0, 8.0, 8.0, 0.0),
            A(3.0, 0.0, 5.0, 5.0),
            A(0.0, 8.0, 3.0, 8.0)
        )

        matrix.transpose() shouldBe matrix2
    }

    "Transposing identity matrix" {
        IDENTITY_MATRIX.transpose() shouldBe IDENTITY_MATRIX
    }

    "Determinant of a 2x2 matrix" {
        Matrix(
            A(1.0, 5.0),
            A(-3.0, 2.0)
        ).determinant() shouldBe 17.0
    }

    "Submatrix of a 3x3 matrix" {
        Matrix(
            A(1.0, 5.0, 0.0),
            A(-3.0, 2.0, 7.0),
            A(0.0, 6.0, -3.0)
        ).subMatrix(0, 2) shouldBe Matrix(
            A(-3.0, 2.0),
            A(0.0, 6.0)
        )
    }

    "Submatrix of a 4x4 matrix" {
        Matrix(
            A(-6.0, 1.0, 1.0, 6.0),
            A(-8.0, 5.0, 8.0, 6.0),
            A(-1.0, 0.0, 8.0, 2.0),
            A(-7.0, 1.0, -1.0, 1.0)
        ).subMatrix(2, 1) shouldBe Matrix(
            A(-6.0, 1.0, 6.0),
            A(-8.0, 8.0, 6.0),
            A(-7.0, -1.0, 1.0)
        )
    }

    "Minor of a 3x3 matrix" {
        val matrix = Matrix(
            A(3.0, 5.0, 0.0),
            A(2.0, -1.0, -7.0),
            A(6.0, -1.0, 5.0)
        )

        matrix.subMatrix(1, 0).determinant() shouldBe 25.0

        matrix.minor(1, 0) shouldBe 25.0
    }

    "Cofactor of a 3x3 matrix" {
        val matrix = Matrix(
            A(3.0, 5.0, 0.0),
            A(2.0, -1.0, -7.0),
            A(6.0, -1.0, 5.0)
        )

        matrix.minor(0, 0) shouldBe -12.0
        matrix.cofactor(0, 0) shouldBe -12.0
        matrix.minor(1, 0) shouldBe 25.0
        matrix.cofactor(1, 0) shouldBe -25.0
    }

    "Determinant of a 3x3 matrix" {
        val matrix = Matrix(
            A(1.0, 2.0, 6.0),
            A(-5.0, 8.0, -4.0),
            A(2.0, 6.0, 4.0)
        )

        matrix.cofactor(0, 0) shouldBe 56.0
        matrix.cofactor(0, 1) shouldBe 12.0
        matrix.cofactor(0, 2) shouldBe -46.0
        matrix.determinant() shouldBe -196.0
    }

    "Determinant of a 4x4 matrix" {
        val matrix = Matrix(
            A(-2.0, -8.0, 3.0, 5.0),
            A(-3.0, 1.0, 7.0, 3.0),
            A(1.0, 2.0, -9.0, 6.0),
            A(-6.0, 7.0, 7.0, -9.0)
        )

        matrix.cofactor(0, 0) shouldBe 690.0
        matrix.cofactor(0, 1) shouldBe 447.0
        matrix.cofactor(0, 2) shouldBe 210.0
        matrix.cofactor(0, 3) shouldBe 51.0
        matrix.determinant() shouldBe -4071.0
    }

    "is matrix invertible" {
        val matrix = Matrix(
            A(6.0, 4.0, 4.0, 4.0),
            A(5.0, 5.0, 7.0, 6.0),
            A(4.0, -9.0, 3.0, -7.0),
            A(9.0, 1.0, 7.0, -6.0)
        )

        matrix.isInvertible() shouldBe true

        val matrix2 = Matrix(
            A(-4.0, 2.0, -2.0, -3.0),
            A(9.0, 6.0, 2.0, 6.0),
            A(0.0, -5.0, 1.0, -5.0),
            A(0.0, 0.0, 0.0, 0.0)
        )

        matrix2.isInvertible() shouldBe false
    }

    "Calculating the inverse of a matrix" {
        val matrix = Matrix(
            A(-5.0, 2.0, 6.0, -8.0),
            A(1.0, -5.0, 1.0, 8.0),
            A(7.0, 7.0, -6.0, -7.0),
            A(1.0, -3.0, 7.0, 4.0)
        )

        val inverted = matrix.inverse()

        matrix.determinant() shouldBe 532.0
        matrix.cofactor(2, 3) shouldBe -160.0
        inverted[3, 2].equal(-160.0 / 532.0) shouldBe true
        matrix.cofactor(3, 2) shouldBe 105.0
        inverted[2, 3].equal(105.0 / 532.0) shouldBe true

        inverted shouldBe Matrix(
            A(0.21805, 0.45113, 0.24060, -0.04511),
            A(-0.80827, -1.45677, -0.44361, 0.52068),
            A(-0.07895, -0.22368, -0.05263, 0.19737),
            A(-0.52256, -0.81391, -0.30075, 0.30639)
        )


        Matrix(
            A(8.0, -5.0, 9.0, 2.0),
            A(7.0, 5.0, 6.0, 1.0),
            A(-6.0, 0.0, 9.0, 6.0),
            A(-3.0, 0.0, -9.0, -4.0)
        ).inverse() shouldBe Matrix(
            A(-0.15385, -0.15385, -0.28205, -0.53846),
            A(-0.07692, 0.12308, 0.02564, 0.03077),
            A(0.35897, 0.35897, 0.43590, 0.92308),
            A(-0.69231, -0.69231, -0.76923, -1.92308)
        )

        Matrix(
            A(9.0, 3.0, 0.0, 9.0),
            A(-5.0, -2.0, -6.0, -3.0),
            A(-4.0, 9.0, 6.0, 4.0),
            A(-7.0, 6.0, 6.0, 2.0)
        ).inverse() shouldBe Matrix(
            A(-0.04074, -0.07778, 0.14444, -0.22222),
            A(-0.07778, 0.03333, 0.36667, -0.33333),
            A(-0.02901, -0.14630, -0.10926, 0.12963),
            A(0.17778, 0.06667, -0.26667, 0.33333)
        )
    }

    "Multiplying a product by it's inverse" {
        val matrix = Matrix(
            A(3.0, -9.0, 7.0, 3.0),
            A(3.0, -8.0, 2.0, -9.0),
            A(-4.0, 4.0, 4.0, 1.0),
            A(-6.0, 5.0, -1.0, 1.0)
        )

        val matrix2 = Matrix(
            A(8.0, 2.0, 2.0, 2.0),
            A(3.0, -1.0, 7.0, 0.0),
            A(7.0, 0.0, 5.0, 4.0),
            A(6.0, -2.0, 0.0, 5.0)
        )

        val m3 = matrix * matrix2
        m3 * matrix2.inverse() shouldBe matrix
    }
})