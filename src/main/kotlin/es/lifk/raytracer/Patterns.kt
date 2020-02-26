package es.lifk.raytracer

import kotlin.math.floor
import kotlin.math.sqrt

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

data class GradientPattern(
    val a: Color,
    val b: Color,
    override val transform: Matrix = IDENTITY_MATRIX
) : Pattern {
    override fun patternAt(point: Tuple): Color {
        val distance = b - a
        val fraction = point.x - floor(point.x)

        return a + distance * fraction
    }
}

data class RingPattern(
    val a: Color,
    val b: Color,
    override val transform: Matrix = IDENTITY_MATRIX
) : Pattern {
    override fun patternAt(point: Tuple): Color {
        if ((floor(sqrt((point.x * point.x) + (point.z * point.z))) % 2).equal(0.0)) return a
        return b
    }
}

data class CheckersPattern(
    val a: Color,
    val b: Color,
    override val transform: Matrix = IDENTITY_MATRIX
) : Pattern {
    override fun patternAt(point: Tuple): Color {
        if (((floor(point.x) + floor(point.y) + floor(point.z)) % 2).equal(0.0)) return a
        return b
    }
}

data class RadialGradientPattern(
    val a: Color,
    val b: Color,
    override val transform: Matrix = IDENTITY_MATRIX
) : Pattern {
    private val ring = RingPattern(a, b, transform)
    override fun patternAt(point: Tuple): Color {
        if (ring.patternAt(point) == a) {
            val distance = b - a
            val fraction = point.y - floor(point.y)

            return a + distance * fraction
        } else {
            val distance = a - b
            val fraction = point.y - floor(point.y)

            return b + distance * fraction
        }
    }
}

data class BlendedPattern(
    val a: Pattern,
    val b: Pattern,
    override val transform: Matrix = IDENTITY_MATRIX
) : Pattern {
    override fun patternAt(point: Tuple): Color {
        return (a.patternAt(point) + b.patternAt(point)) * 0.5
    }
}