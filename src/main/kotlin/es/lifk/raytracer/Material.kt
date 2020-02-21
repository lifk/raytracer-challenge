package es.lifk.raytracer

data class Material(
    val color: Color = Color(1.0, 1.0, 1.0),
    val ambient: Double = 0.1,
    val diffuse: Double = 0.9,
    val specular: Double = 0.9,
    val shininess: Double = 200.0
)