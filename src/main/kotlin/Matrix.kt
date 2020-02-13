import java.lang.Exception

class Matrix(vararg data: Array<Double>) {
    private val data = arrayOf(*data)
    operator fun get(x: Int, y: Int) = data[x][y]
    operator fun set(x: Int, y: Int, value: Double) {
        try {
            data[x][y] = value
        } catch (e: Exception) {
            println("position not available on canvas")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        data.forEachIndexed { pos, row ->
            row.forEachIndexed { pos2, value ->
                if (!value.equal(other[pos, pos2])) return false
            }
        }

        return true
    }

    override fun hashCode(): Int {
        return data.contentDeepHashCode()
    }
}

inline fun <reified T> A(vararg elements: T): Array<T> = arrayOf(*elements)