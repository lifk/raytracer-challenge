package es.lifk.raytracer

data class World(var light: PointLight? = null) {
    val objects: MutableList<Shape> = mutableListOf()

    fun intersect(ray: Ray): Array<Intersection> {
        return objects.flatMap { ray.intersect(it).toList() }.sortedBy { it.t }.toTypedArray()
    }

    fun shadeHit(comps: Computation, remaining: Int = 4): Color {
        return lighting(
            material = comps.obj.material,
            obj = comps.obj,
            light = light!!,
            point = comps.point,
            eyeVector = comps.eyeV,
            normal = comps.normalV,
            inShadow = isShadowed(comps.overPoint)
        ) + reflectedColor(comps, remaining)
    }

    fun colorAt(ray: Ray, remaining: Int = 4): Color {
        val hit = intersect(ray).hit()

        return if (hit != null) {
            shadeHit(hit.prepareComputations(ray), remaining)
        } else {
            Color(0.0, 0.0, 0.0)
        }
    }

    fun reflectedColor(comps: Computation, remaining: Int = 4): Color {
        if (comps.obj.material.reflective.equal(0.0) || remaining <= 0) return Color.BLACK

        val reflectRay = Ray(comps.overPoint, comps.reflectV)
        val color = colorAt(reflectRay, remaining - 1)

        return color * comps.obj.material.reflective
    }

    fun isShadowed(point: Tuple): Boolean {
        val v = light!!.position - point
        val distance = v.magnitude()
        val direction = v.normalize()

        val shadowRay = Ray(point, direction)
        val hit = intersect(shadowRay).hit()

        return hit != null && hit.t < distance
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