package es.lifk.raytracer.exercises

import es.lifk.raytracer.*
import java.io.File
import kotlin.math.PI

fun main() {
    val floor = Plane(
        translation(0.0, 0.0, 20.0),
        Material(Color(1.0, 0.9, 0.9), specular = 0.0, reflective = 0.9)
    )

    val middleSphere = Sphere(
        transform = translation(-0.5, 1.0, -0.5),
        material = Material(
            Color(0.1, 1.0, 0.5),
            diffuse = 0.7,
            specular = 0.3,
            pattern = CheckersPattern(Color(0.2, 0.5, 0.6), Color.WHITE, scaling(0.2, 0.2, 0.2))
        )
    )

    val rightSphere = Sphere(
        transform = translation(1.5, 1.5, -0.5) * scaling(1.5, 1.5, 1.5),
        material = Material(
            Color(0.5, 1.0, 0.1),
            diffuse = 0.7,
            specular = 0.3,
            pattern = RadialGradientPattern(Color(1.0, 0.0, 0.0), Color(0.0, 1.0, 0.0), scaling(0.3, 0.3, 0.3))
        )
    )

    val leftSphere = Sphere(
        transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33),
        material = Material(
            Color(1.0, 0.8, 0.1),
            diffuse = 0.7,
            specular = 0.3,
            pattern = RingPattern(Color(0.63, 0.25, 0.98), Color(0.23, 0.78, 0.24), scaling(0.1, 0.1, 0.1))
        )
    )

    val world = World(PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0)))

    world.objects.add(floor)
    world.objects.add(middleSphere)
    world.objects.add(rightSphere)
    world.objects.add(leftSphere)

    val camera = Camera(
        200,
        100,
        PI / 2,
        transform = viewTransform(point(0.0, 1.5, -5.0), point(0.0, 1.0, 0.0), vector(0.0, 1.0, 0.0))
    )

    val canvas = camera.render(world)


    File("test4.ppm").writeText(canvasToPPM(canvas))
}