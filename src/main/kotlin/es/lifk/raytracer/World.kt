package es.lifk.raytracer

data class World(var light: PointLight? = null) {
    val objects: MutableList<Sphere> = mutableListOf()

    fun intersect(ray: Ray): Array<Intersection> {
        return objects.flatMap { ray.intersect(it).toList() }.sortedBy { it.t }.toTypedArray()
    }

    fun shadeHit(comps: Computation): Color {
        return lighting(comps.obj.material, light!!, comps.point, comps.eyeV, comps.normalV)
    }

    fun colorAt(ray: Ray): Color {
        val hit = intersect(ray).hit()

        return if (hit != null) {
            shadeHit(hit.prepareComputations(ray))
        } else {
            Color(0.0, 0.0, 0.0)
        }
    }
}

fun defaultWorld(): World {
    val w = World(PointLight(point(-10.0, 10.0, -10.0), Color(1.0, 1.0, 1.0)))
    val s1 = Sphere(
        material = Material(
            color = Color(0.8, 1.0, 0.6),
            diffuse = 0.7,
            specular = 0.2
        )
    )

    val s2 = Sphere(transform = scaling(0.5, 0.5, 0.5))

    w.objects.add(s1)
    w.objects.add(s2)

    return w
}

fun viewTransform(from: Tuple, to: Tuple, up: Tuple): Matrix {
    val forward = (to - from).normalize()
    val upn = up.normalize()
    val left = forward cross upn
    val trueUp = left cross forward

    val orientation = Matrix(
        A(left.x, left.y, left.z, 0.0),
        A(trueUp.x, trueUp.y, trueUp.z, 0.0),
        A(-forward.x, -forward.y, -forward.z, 0.0),
        A(0.0, 0.0, 0.0, 1.0)
    )

    return orientation * translation(-from.x, -from.y, -from.z)
}