package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.sqrt

class LightsSpec : StringSpec({
    val material = Material()
    val position = point(0.0, 0.0, 0.0)

    "Initializing a point light" {
        val pl = PointLight(point(0.0, 0.0, 0.0), Color(1.0, 0.0, 0.0))
        pl.position shouldBe point(0.0, 0.0, 0.0)
        pl.intensity shouldBe Color(1.0, 0.0, 0.0)
    }

    "Lighting with the eye between the light and the surface" {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normal = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))

        val result = lighting(material, light, position, eyeV, normal)

        result shouldBe Color(1.9, 1.9, 1.9)
    }

    "Lighting with the eye between the light and the surface, eye offset 45 degrees" {
        val eyeV = vector(0.0, sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0)
        val normal = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, -10.0), Color(1.0, 1.0, 1.0))

        val result = lighting(material, light, position, eyeV, normal)

        result shouldBe Color(1.0, 1.0, 1.0)
    }

    "Lighting with the eye between the light and the surface, light offset 45 degrees" {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normal = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))

        val result = lighting(material, light, position, eyeV, normal)

        result shouldBe Color(0.7364, 0.7364, 0.7364)
    }

    "Lighting with the eye in the path of the reflection vector" {
        val eyeV = vector(0.0, -sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0)
        val normal = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 10.0, -10.0), Color(1.0, 1.0, 1.0))

        val result = lighting(material, light, position, eyeV, normal)

        result shouldBe Color(1.6364, 1.6364, 1.6364)
    }

    "lighting with the light behind the surface" {
        val eyeV = vector(0.0, 0.0, -1.0)
        val normal = vector(0.0, 0.0, -1.0)
        val light = PointLight(point(0.0, 0.0, 10.0), Color(1.0, 1.0, 1.0))

        val result = lighting(material, light, position, eyeV, normal)

        result shouldBe Color(0.1, 0.1, 0.1)
    }
})