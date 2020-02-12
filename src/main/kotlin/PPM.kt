fun canvasToPPM(canvas: Canvas) = """P3
${canvas.width} ${canvas.height}
255
${(0 until canvas.height).flatMap { x ->
    val line = (0 until canvas.width).joinToString(" ") { y ->
        val color = canvas[y, x].getAs255()
        "${color.first} ${color.second} ${color.third}"
    }
    if (line.length > 70) {
        var acc = ""
        var acc2 = ""
        line.split(" ").forEach {
            if (acc.length + it.length < 70) {
                acc += "$it "
            } else acc2 += "$it "
        }

        listOf(acc.trim(), acc2.trim())
    } else listOf(line)
}.joinToString("\n")}
"""