import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class CanvasSpec : StringSpec({
    "canvas is black" {
        Canvas(1, 1)[0, 0] shouldBe Color(0.0, 0.0, 0.0)
    }

    "set color to red" {
        val redCanvas = Canvas(10, 10)
        redCanvas[2, 3] = Color(1.0, 0.0, 0.0)
        redCanvas[2, 3] shouldBe Color(1.0, 0.0, 0.0)
    }
})