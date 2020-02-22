package es.lifk.raytracer.exercises

import es.lifk.raytracer.*
import java.io.File
import kotlin.math.PI

fun main() {
    val floor = Sphere(
        scaling(10.0, 0.01, 10.0),
        Material(Color(1.0, 0.9, 0.9), specular = 0.0)
    )

    val leftWall = Sphere(
        translation(0.0, 0.0, 5.0) * rotationY(-PI / 4) * rotationX(PI / 2) * scaling(10.0, 0.01, 10.0),
        floor.material
    )

    val rightWall = Sphere(
        translation(0.0, 0.0, 5.0) * rotationY(PI / 4) * rotationX(PI / 2) * scaling(10.0, 0.01, 10.0),
        floor.material
    )

    val middleSphere = Sphere(
        transform = translation(-0.5, 1.0, -0.5),
        material = Material(Color(0.1, 1.0, 0.5), diffuse = 0.7, specular = 0.3)
    )

    val rightSphere = Sphere(
        transform = translation(1.5, 0.5, -0.5) * scaling(0.5, 0.5, 0.5),
        material = Material(Color(0.5, 1.0, 0.1), diffuse = 0.7, specular = 0.3)
    )

    val leftSphere = Sphere(
        transform = translation(-1.5, 0.33, -0.75) * scaling(0.33, 0.33, 0.33),
        material = Material(Color(1.0, 0.8, 0.1), diffuse = 0.7, specular = 0.3)
    )

    val world = World(PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0)))

    world.objects.add(floor)
    world.objects.add(leftWall)
    world.objects.add(rightWall)
    world.objects.add(middleSphere)
    world.objects.add(rightSphere)
    world.objects.add(leftSphere)

    val camera = Camera(
        500,
        250,
        PI / 2,
        transform = viewTransform(point(0.0, 1.5, -5.0), point(0.0, 1.0, 0.0), vector(0.0, 1.0, 0.0))
    )

    val canvas = camera.render(world)


    File("test4.ppm").writeText(canvasToPPM(canvas))
}