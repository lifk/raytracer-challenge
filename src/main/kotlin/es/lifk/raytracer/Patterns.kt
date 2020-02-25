package es.lifk.raytracer

import kotlin.math.floor

interface Pattern {
    val transform: Matrix

    fun patternAtShape(obj: Shape, point: Tuple): Color {
        val objPoint = obj.transform.inverse() * point
        val patternPoint = transform.inverse() * objPoint

        return patternAt(patternPoint)
    }

    fun patternAt(point: Tuple): Color
}

data class StripePattern(
    val a: Color,
    val b: Color,
    override val transform: Matrix = IDENTITY_MATRIX
) : Pattern {
    override fun patternAt(point: Tuple): Color {
        if (floor(point.x) % 2 == 0.0) return a
        return b
    }
}