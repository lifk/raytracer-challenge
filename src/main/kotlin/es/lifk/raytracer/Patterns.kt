package es.lifk.raytracer

import kotlin.math.floor

data class StripePattern(val a: Color, val b: Color, val transform: Matrix = IDENTITY_MATRIX) {
    fun stripeAt(point: Tuple): Color {
        if (floor(point.x) % 2 == 0.0) return a
        return b
    }

    fun stripeAtObject(obj: Shape, point: Tuple): Color {
        val objPoint = obj.transform.inverse() * point
        val patternPoint = transform.inverse() * objPoint

        return stripeAt(patternPoint)
    }
}