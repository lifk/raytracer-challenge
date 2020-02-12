import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PPMSpec : StringSpec({
    "PPM header" {
        val ppm = canvasToPPM(Canvas(10, 20)).lines()
        ppm[0] shouldBe "P3"
        ppm[1] shouldBe "10 20"
        ppm[2] shouldBe "255"
    }

    "PPM pixel data" {
        val canvas = Canvas(5, 3)
        canvas[0, 0] = Color(1.5, 0.0, 0.0)
        canvas[2, 1] = Color(0.0, 0.5, 0.0)
        canvas[4, 2] = Color(-0.5, 0.0, 1.0)
        val ppm = canvasToPPM(canvas).lines()
        ppm[3] shouldBe "255 0 0 0 0 0 0 0 0 0 0 0 0 0 0"
        ppm[4] shouldBe "0 0 0 0 0 0 0 128 0 0 0 0 0 0 0"
        ppm[5] shouldBe "0 0 0 0 0 0 0 0 0 0 0 0 0 0 255"
    }

    "PPM split long lines" {
        val canvas = Canvas(10, 2)
        (0..9).forEach {
            canvas[it, 1] = Color(1.0, 0.8, 0.6)
            canvas[it, 0] = Color(1.0, 0.8, 0.6)
        }

        val ppm = canvasToPPM(canvas).lines()
        ppm[3] shouldBe "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204"
        ppm[4] shouldBe "153 255 204 153 255 204 153 255 204 153 255 204 153"
        ppm[5] shouldBe "255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204"
        ppm[6] shouldBe "153 255 204 153 255 204 153 255 204 153 255 204 153"
    }

    "canvas ends in newline" {
        canvasToPPM(Canvas(5, 3)).last() shouldBe '\n'
    }
})