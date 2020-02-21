package es.lifk.raytracer

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.PI
import kotlin.math.sqrt

class SphereSpec : StringSpec({
    "The normal of a sphere at the point on the x/y/z axis" {
        Sphere().normalAt(point(1.0, 0.0, 0.0)) shouldBe vector(1.0, 0.0, 0.0)
        Sphere().normalAt(point(0.0, 1.0, 0.0)) shouldBe vector(0.0, 1.0, 0.0)
        Sphere().normalAt(point(0.0, 0.0, 1.0)) shouldBe vector(0.0, 0.0, 1.0)
    }

    "Normal of a sphere in a non axial point" {
        Sphere().normalAt(point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0)) shouldBe vector(
            sqrt(3.0) / 3.0,
            sqrt(3.0) / 3.0,
            sqrt(3.0) / 3.0
        )
    }

    "Normals are always normalized" {
        val normal = Sphere().normalAt(point(sqrt(3.0) / 3.0, sqrt(3.0) / 3.0, sqrt(3.0) / 3.0))
        normal shouldBe normal.normalize()
    }

    "Computing the normal on a translated sphere" {
        val sphere = Sphere()
        sphere.transform = translation(0.0, 1.0, 0.0)
        val n = sphere.normalAt(point(0.0, 1.70711, -0.70711))
        n shouldBe vector(0.0, 0.70711, -0.70711)
    }

    "Computing the normal on a transformed sphere" {
        val sphere = Sphere()
        sphere.transform = (scaling(1.0, 0.5, 1.0) * rotationZ(PI / 5))
        val n = sphere.normalAt(point(0.0, sqrt(2.0) / 2.0, -sqrt(2.0) / 2.0))
        n shouldBe vector(0.0, 0.97014, -0.24254)
    }


})