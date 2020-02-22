package es.lifk.raytracer

data class Intersection(val t: Double, val obj: Sphere) {
    fun prepareComputations(ray: Ray): Computation {
        val point = ray.position(t)
        val normal = obj.normalAt(point)

        return if (normal dot -ray.direction < 0) {
            Computation(
                t = t,
                obj = obj,
                point = point,
                eyeV = -ray.direction,
                normalV = -normal,
                inside = true
            )
        } else {
            Computation(
                t = t,
                obj = obj,
                point = point,
                eyeV = -ray.direction,
                normalV = normal
            )
        }
    }
}

data class Computation(
    val t: Double,
    val obj: Sphere,
    val point: Tuple,
    val eyeV: Tuple,
    val normalV: Tuple,
    val inside: Boolean = false
)

fun intersections(vararg intersection: Intersection): Array<Intersection> {
    return arrayOf(*intersection)
}

fun Array<Intersection>.hit(): Intersection? {
    return this.asSequence().filter { it.t >= 0.0 }.sortedBy { it.t }.firstOrNull()
}