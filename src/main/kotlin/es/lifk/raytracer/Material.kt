package es.lifk.raytracer

data class Material(
    val color: Color = Color(1.0, 1.0, 1.0),
    var ambient: Double = 0.1,
    var diffuse: Double = 0.9,
    var specular: Double = 0.9,
    var shininess: Double = 200.0,
    var pattern: Pattern? = null,
    val reflective: Double = 0.0,
    var transparency: Double = 0.0,
    var refractiveIndex: Double = 1.0
)