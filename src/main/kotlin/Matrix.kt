import java.lang.Exception

val IDENTITY_MATRIX = Matrix(
    A(1.0, 0.0, 0.0, 0.0),
    A(0.0, 1.0, 0.0, 0.0),
    A(0.0, 0.0, 1.0, 0.0),
    A(0.0, 0.0, 0.0, 1.0)
)

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

    fun transpose(): Matrix {
        val newMatrix = Matrix(*data.indices.map { DoubleArray(data.size).toTypedArray() }.toTypedArray())

        data.indices.forEach { row ->
            data[0].indices.forEach { column ->
                newMatrix[row, column] = this[column, row]
            }
        }

        return newMatrix
    }

    operator fun times(other: Matrix): Matrix {
        val newMatrix = Matrix(*data.indices.map { DoubleArray(data.size).toTypedArray() }.toTypedArray())

        data.indices.forEach { row ->
            data[0].indices.forEach { column ->
                newMatrix[row, column] = data.indices.sumByDouble { k ->
                    this[row, k] * other[k, column]
                }
            }
        }

        return newMatrix
    }

    operator fun times(other: Tuple): Tuple {
        val values = data.indices.map { row ->
            this[row, 0] * other.x +
                    this[row, 1] * other.y +
                    this[row, 2] * other.z +
                    this[row, 3] * other.w
        }

        return Tuple(values[0], values[1], values[2], values[3])
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

    override fun toString(): String {
        return data.joinToString("\n") { it.joinToString(" | ") }
    }

    override fun hashCode(): Int {
        return data.contentDeepHashCode()
    }
}

inline fun <reified T> A(vararg elements: T): Array<T> = arrayOf(*elements)