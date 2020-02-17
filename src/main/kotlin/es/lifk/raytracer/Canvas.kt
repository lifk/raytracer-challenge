package es.lifk.raytracer

data class Canvas(val width: Int, val height: Int) {
    private val data: Array<Array<Color>> = (0..width).map { column ->
        (0..height).map { row -> Color(0.0, 0.0, 0.0) }.toTypedArray()
    }.toTypedArray()

    operator fun get(x: Int, y: Int) = data[x][y]
    operator fun set(x: Int, y: Int, color: Color) {
        try {
            data[x][y] = color
        } catch (e: Exception) {
            println("position not available on canvas")
        }
    }
}