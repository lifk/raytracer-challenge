package es.lifk.raytracer

data class Material(
    val color: Color = Color(1.0, 1.0, 1.0),
    var ambient: Double = 0.1,
    var diffuse: Double = 0.9,
    var specular: Double = 0.9,
    var shininess: Double = 200.0,
    val pattern: Pattern? = null
)