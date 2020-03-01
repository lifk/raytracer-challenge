package es.lifk.raytracer

data class Intersection(val t: Double, val obj: Shape) {
    fun prepareComputations(ray: Ray, intersections: List<Intersection> = emptyList()): Computation {
        val point = ray.position(t)
        val normal = obj.normalAt(point)

        val containers = mutableListOf<Shape>()

        var n1: Double = 0.0
        var n2: Double = 0.0

        for (i in intersections) {
            if (i == this) {
                n1 = if (containers.isEmpty()) {
                    1.0
                } else {
                    containers.last().material.refractiveIndex
                }
            }

            if (i.obj in containers) {
                containers.remove(i.obj)
            } else {
                containers.add(i.obj)
            }

            if (i == this) {
                n2 = if (containers.isEmpty()) {
                    1.0
                } else {
                    containers.last().material.refractiveIndex
                }

                break
            }
        }

        return if (normal dot -ray.direction < 0) {
            Computation(
                t = t,
                obj = obj,
                point = point,
                eyeV = -ray.direction,
                normalV = -normal,
                reflectV = ray.direction.reflect(-normal),
                inside = true,
                n1 = n1,
                n2 = n2
            )
        } else {
            Computation(
                t = t,
                obj = obj,
                point = point,
                eyeV = -ray.direction,
                normalV = normal,
                reflectV = ray.direction.reflect(normal),
                n1 = n1,
                n2 = n2
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
    val inside: Boolean = false,
    val n1: Double,
    val n2: Double
) {
    val overPoint = point + normalV * EPSILON
    val underPoint = point - normalV * EPSILON
}

fun intersections(vararg intersection: Intersection): Array<Intersection> {
    return arrayOf(*intersection)
}

fun Array<Intersection>.hit(): Intersection? {
    return this.asSequence().filter { it.t >= 0.0 }.sortedBy { it.t }.firstOrNull()
}