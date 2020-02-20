package es.lifk.raytracer

fun canvasToPPM(canvas: Canvas) = """P3
${canvas.width} ${canvas.height}
255
${(0 until canvas.height).flatMap { x ->
    val line = (0 until canvas.width).joinToString(" ") { y ->
        val color = canvas[y, x].getAs255()
        "${color.first} ${color.second} ${color.third}"
    }
    if (line.length > 70) {
        val mutableList = mutableListOf("")

        var lines = 0

        line.split(" ").forEach {
            if (mutableList[lines].length + it.length < 70) {
                mutableList[lines] = mutableList[lines] + "$it "
            } else {
                lines++
                mutableList.add("$it ")
            }
        }

        mutableList.map { it.trim() }
    } else listOf(line)
}.joinToString("\n")}
"""