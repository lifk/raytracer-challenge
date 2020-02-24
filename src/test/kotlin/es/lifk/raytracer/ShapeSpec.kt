package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.PI
import kotlin.math.sqrt

class ShapeSpec : StringSpec({
    "A shape has a default material" {
        TestShape().material shouldBe Material()
    }

    "A shape may be assigned a material" {
        val material = Material(ambient = 1.0)
        val sphere = TestShape(material = material)
        sphere.material shouldBe material
    }

    "Intersecting a scaled shape with a ray" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val testShape = TestShape(transform = scaling(2.0, 2.0, 2.0))
        ray.intersect(testShape)

        testShape.testRay.origin shouldBe point(0.0, 0.0, -2.5)
        testShape.testRay.direction shouldBe vector(0.0, 0.0, 0.5)
    }

    "Intersecting a translated shape with a ray" {
        val ray = Ray(point(0.0, 0.0, -5.0), vector(0.0, 0.0, 1.0))
        val testShape = TestShape(transform = translation(5.0, 0.0, 0.0))
        ray.intersect(testShape)

        testShape.testRay.origin shouldBe point(-5.0, 0.0, -5.0)
        testShape.testRay.direction shouldBe vector(0.0, 0.0, 1.0)
    }

    "Computing the normal on a translated shape" {
        val testShape = TestShape(transform = translation(0.0, 1.0, 0.0))

        testShape.normalAt(point(0.0, 1.70711, -0.70711)) shouldBe vector(0.0, 0.70711, -0.70711)
    }

    "Computing the normal on a transformed shape" {
        val testShape = TestShape(transform = scaling(1.0, 0.5, 1.0) * rotationZ(PI / 5))

        testShape.normalAt(point(0.0, sqrt(2.0) / 2, -sqrt(2.0) / 2)) shouldBe vector(0.0, 0.97014, -0.24254)
    }
})