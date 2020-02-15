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
})