package es.lifk.raytracer.exercises

import es.lifk.raytracer.Canvas
import es.lifk.raytracer.Color
import es.lifk.raytracer.Tuple
import es.lifk.raytracer.canvasToPPM
import es.lifk.raytracer.point
import es.lifk.raytracer.vector
import java.io.File
import kotlin.math.roundToInt

data class Projectile(val pos: Tuple, val velocity: Tuple)
data class Environment(val gravity: Tuple, val wind: Tuple)

fun tick(env: Environment, projectile: Projectile): Projectile {
    return projectile.copy(
        pos = projectile.pos + projectile.velocity,
        velocity = projectile.velocity + env.gravity + env.wind
    )
}

fun main(){
    var projectile = Projectile(
        point(0.0, 1.0, 0.0),
        vector(1.0, 1.8, 0.0).normalize() * 11.25
    )
    val env = Environment(
        vector(0.0, -0.1, 0.0),
        vector(-0.01, 0.0, 0.0)
    )
    val canvas = Canvas(900, 550)


    var ticks = 0
    while (projectile.pos.y > 0) {
        ticks++
        projectile = tick(env, projectile)
        canvas[projectile.pos.x.roundToInt(), 550 - projectile.pos.y.roundToInt()] =
            Color(1.0, 0.0, 0.0)
    }

    File("test.ppm").writeText(canvasToPPM(canvas))

}