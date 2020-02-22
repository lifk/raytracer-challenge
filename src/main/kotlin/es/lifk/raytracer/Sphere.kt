package es.lifk.raytracer

data class Sphere(var transform: Matrix = IDENTITY_MATRIX, val material: Material = Material()) {
    fun normalAt(point: Tuple): Tuple {
        val objectPoint = transform.inverse() * point
        val objectNormal = objectPoint - point(0.0, 0.0, 0.0)
        val worldNormal = transform.inverse().transpose() * objectNormal

        return worldNormal.copy(w = 0.0).normalize()
    }
}