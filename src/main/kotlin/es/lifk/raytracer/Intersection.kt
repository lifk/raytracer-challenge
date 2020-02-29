package es.lifk.raytracer

data class Intersection(val t: Double, val obj: Shape) {
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
                reflectV = ray.direction.reflect(-normal),
                inside = true
            )
        } else {
            Computation(
                t = t,
                obj = obj,
                point = point,
                eyeV = -ray.direction,
                normalV = normal,
                reflectV = ray.direction.reflect(normal)
            )
        }
    }
}

data class Computation(
    val t: Double,
    val obj: Shape,
    val point: Tuple,
    val eyeV: Tuple,
    val normalV: Tuple,
    val reflectV: Tuple,
    val inside: Boolean = false
) {
    val overPoint = point + normalV * EPSILON
}

fun intersections(vararg intersection: Intersection): Array<Intersection> {
    return arrayOf(*intersection)
}

fun Array<Intersection>.hit(): Intersection? {
    return this.asSequence().filter { it.t >= 0.0 }.sortedBy { it.t }.firstOrNull()
}