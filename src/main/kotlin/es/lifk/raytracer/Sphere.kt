package es.lifk.raytracer

class Sphere(var transform: Matrix = IDENTITY_MATRIX) {
    fun normalAt(point: Tuple): Tuple {
        val objectPoint = transform.inverse() * point
        val objectNormal = objectPoint - point(0.0, 0.0, 0.0)
        val worldNormal = transform.inverse().transpose() * objectNormal

        return worldNormal.copy(w = 0.0).normalize()
    }
}