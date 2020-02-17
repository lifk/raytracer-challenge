package es.lifk.raytracer

data class Projectile(val pos: Tuple, val velocity: Tuple)
data class Environment(val gravity: Tuple, val wind: Tuple)

fun tick(env: Environment, projectile: Projectile): Projectile {
    return projectile.copy(
        pos = projectile.pos + projectile.velocity,
        velocity = projectile.velocity + env.gravity + env.wind
    )
}

