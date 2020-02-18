package es.lifk.raytracer

data class Intersection(val t: Double, val obj: Any)

fun intersections(vararg intersection: Intersection): Array<Intersection> {
    return arrayOf(*intersection)
}

fun Array<Intersection>.hit(): Intersection? {
    return this.asSequence().filter { it.t >= 0.0 }.sortedBy { it.t }.firstOrNull()
}