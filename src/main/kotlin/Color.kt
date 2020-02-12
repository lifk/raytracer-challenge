import kotlin.math.roundToInt

data class Color(val red: Double, val green: Double, val blue: Double) {
    operator fun plus(other: Color) = Color(red + other.red, green + other.green, blue + other.blue)
    operator fun minus(other: Color) = Color(red - other.red, green - other.green, blue - other.blue)
    operator fun times(value: Double) = Color(red * value, green * value, blue * value)
    operator fun times(value: Color) = Color(red * value.red, green * value.green, blue * value.blue)

    fun getAs255(): Triple<Int, Int, Int> {
        val r = if (red * 255 > 255) 255 else (red * 255).roundToInt()
        val g = if (green * 255 > 255) 255 else (green * 255).roundToInt()
        val b = if (blue * 255 > 255) 255 else (blue * 255).roundToInt()
        return Triple(
            if (r < 0) 0 else r,
            if (g < 0) 0 else g,
            if (b < 0) 0 else b
        )
    }

    override operator fun equals(other: Any?): Boolean {
        if (other !is Color) return false
        if (red.equal(other.red) && green.equal(other.green) && blue.equal(other.blue)) return true
        return false
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        return result
    }
}