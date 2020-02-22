package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.PI
import kotlin.math.sqrt

class CameraSpec : StringSpec({
    "Constructing a camera" {
        val c = Camera(160, 120, PI / 2)
        c.hSize shouldBe 160
        c.vSize shouldBe 120
        c.fieldOfView shouldBe PI / 2
        c.transform shouldBe IDENTITY_MATRIX
    }

    "Pixel size for an horizontal canvas" {
        val c = Camera(200, 125, PI / 2)
        c.pixelSize.equal(0.01) shouldBe true
    }

    "Pixel size for a vertical canvas" {
        val c = Camera(125, 200, PI / 2)
        c.pixelSize.equal(0.01) shouldBe true
    }

    "Constructing a ray though the center of the canvas" {
        val camera = Camera(201, 101, PI / 2)
        val ray = camera.rayForPixel(100, 50)
        ray.origin shouldBe point(0.0, 0.0, 0.0)
        ray.direction shouldBe vector(0.0, 0.0, -1.0)
    }

    "Constructing a ray though the corner of the canvas" {
        val camera = Camera(201, 101, PI / 2)
        val ray = camera.rayForPixel(0, 0)
        ray.origin shouldBe point(0.0, 0.0, 0.0)
        ray.direction shouldBe vector(0.66519, 0.33259, -0.66851)
    }

    "Constructing a ray when the camera is transformed" {
        val camera = Camera(201, 101, PI / 2, rotationY(PI / 4) * translation(0.0, -2.0, 5.0))
        val ray = camera.rayForPixel(100, 50)
        ray.origin shouldBe point(0.0, 2.0, -5.0)
        ray.direction shouldBe vector(sqrt(2.0) / 2.0, 0.0, -sqrt(2.0) / 2.0)
    }

    "rendering a world with a camera" {
        val w = defaultWorld()
        val camera = Camera(
            11,
            11,
            PI / 2,
            viewTransform(point(0.0, 0.0, -5.0), point(0.0, 0.0, 0.0), vector(0.0, 1.0, 0.0))
        )

        val render = camera.render(w)
        render[5, 5] shouldBe Color(0.38066, 0.47583, 0.2855)
    }
})