package es.lifk.raytracer

import io.kotlintest.specs.StringSpec
import java.io.File
import kotlin.math.roundToInt

class ProjectileSpec : StringSpec({
    "launch projectile" {
        var projectile =
            Projectile(
                point(
                    0.0,
                    1.0,
                    0.0
                ), vector(1.0, 1.0, 0.0).normalize()
            )
        val env = Environment(
            vector(0.0, -0.1, 0.0),
            vector(-0.01, 0.0, 0.0)
        )

        var ticks = 0
        while (projectile.pos.y > 0) {
            ticks++
            projectile = tick(env, projectile)
            println(projectile.pos)
        }
        println(ticks)
    }

    "launch projectile on canvas" {
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
})