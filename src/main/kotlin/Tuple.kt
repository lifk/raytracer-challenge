import kotlin.math.pow
import kotlin.math.sqrt

data class Tuple(val x: Double, val y: Double, val z: Double, val w: Double) {
    operator fun plus(other: Tuple) = Tuple(x + other.x, y + other.y, z + other.z, w + other.w)
    operator fun minus(other: Tuple) = Tuple(x - other.x, y - other.y, z - other.z, w - other.w)
    operator fun unaryMinus() = vector(0.0, 0.0, 0.0) - this
    operator fun times(value: Double) = Tuple(x * value, y * value, z * value, w * value)
    operator fun div(value: Double) = Tuple(x / value, y / value, z / value, w / value)

    fun magnitude(): Double = sqrt(x.pow(2) + y.pow(2) + z.pow(2) + w.pow(2))
    fun normalize(): Tuple = Tuple(x / magnitude(), y / magnitude(), z / magnitude(), w / magnitude())

    infix fun dot(other: Tuple): Double = (x * other.x) + (y * other.y) + (z * other.z) + (w * other.w)

    infix fun cross(other: Tuple): Tuple =
        vector(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)

    override operator fun equals(other: Any?): Boolean {
        if (other !is Tuple) return false
        if (x.equal(other.x) && y.equal(other.y) && z.equal(other.z) && w.equal(other.w)) return true
        return false
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + w.hashCode()
        return result
    }
}

fun point(x: Double, y: Double, z: Double) = Tuple(x, y, z, 1.0)
fun vector(x: Double, y: Double, z: Double) = Tuple(x, y, z, 0.0)
