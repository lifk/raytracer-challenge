package es.lifk.raytracer

data class Ray(val origin: Tuple, val direction: Tuple) {
    fun position(t: Double): Tuple {
        return origin + direction * t
    }

    fun intersect(other: Shape): Array<Intersection> {
        val ray = transform(other.transform.inverse())

        return other.localIntersect(ray)
    }

    fun transform(matrix: Matrix): Ray {
        return Ray(matrix * origin, matrix * direction)
    }
}