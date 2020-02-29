package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class MaterialSpec : StringSpec({
    "Default reflection is 0" {
        val m = Material()
        m.reflective shouldBe 0.0
    }
})