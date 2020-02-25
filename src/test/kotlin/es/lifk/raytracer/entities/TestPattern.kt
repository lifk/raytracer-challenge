package es.lifk.raytracer.entities

import es.lifk.raytracer.*

class TestPattern(override val transform: Matrix = IDENTITY_MATRIX) : Pattern {
    override fun patternAt(point: Tuple): Color {
        return Color(point.x, point.y, point.z)
    }
}