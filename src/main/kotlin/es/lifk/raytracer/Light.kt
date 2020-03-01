package es.lifk.raytracer

data class PointLight(val position: Tuple, val intensity: Color)

fun lighting(
    material: Material,
    obj: Shape,
    light: PointLight,
    point: Tuple,
    eyeVector: Tuple,
    normal: Tuple,
    inShadow: Boolean = false
): Color {
    val color = if (material.pattern != null) material.pattern!!.patternAtShape(obj, point) else material.color
    val effectiveColor = color * light.intensity
    val lightV = (light.position - point).normalize()
    val ambient = color * material.ambient

    val lightDotNormal = lightV dot normal

    var diffuse = Color(0.0, 0.0, 0.0)
    var specular = Color(0.0, 0.0, 0.0)

    if (lightDotNormal >= 0) {
        diffuse = effectiveColor * material.diffuse * lightDotNormal
        val reflectV = (-lightV).reflect(normal)
        val reflectDotEye = reflectV dot eyeVector

        specular = if (reflectDotEye < 0) {
            Color(0.0, 0.0, 0.0)
        } else {
            val factor = Math.pow(reflectDotEye, material.shininess)
            light.intensity * material.specular * factor
        }
    }

    if (inShadow) return ambient

    return ambient + diffuse + specular
}