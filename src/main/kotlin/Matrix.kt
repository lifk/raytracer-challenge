import java.lang.Exception
import kotlin.math.min

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
            println("Cannot set value on Matrix, out of bounds exception")
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

    fun determinant(): Double {
        return if (data.size == 2) {
            this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]
        } else {
            var det = 0.0
            data.indices.forEach { col ->
                det += this[0, col] * cofactor(0, col)
            }
            det
        }
    }

    fun subMatrix(row: Int, column: Int): Matrix {
        val newMatrix =
            Matrix(*(0 until data.size - 1).map { DoubleArray(data.size - 1).toTypedArray() }.toTypedArray())

        (0 until data.size - 1).forEach { i ->
            (0 until data[0].size - 1).forEach { j ->
                val x = if (i >= row) i + 1 else i
                val y = if (j >= column) j + 1 else j
                newMatrix[i, j] = this[x, y]
            }
        }

        return newMatrix
    }

    fun isInvertible(): Boolean {
        return !determinant().equal(0.0)
    }

    fun inverse(): Matrix {
        if (!isInvertible()) throw Exception("Matrix is not invertible")

        val newMatrix = Matrix(*data.indices.map { DoubleArray(data.size).toTypedArray() }.toTypedArray())

        data.indices.forEach { row ->
            data[0].indices.forEach { column ->
                val cofactor = cofactor(row, column)

                newMatrix[column, row] = cofactor / determinant()
            }
        }


        return newMatrix
    }

    fun minor(row: Int, column: Int): Double {
        return subMatrix(row, column).determinant()
    }

    fun cofactor(row: Int, column: Int): Double {
        return if ((row + column) % 2 == 0) {
            minor(row, column)
        } else {
            -minor(row, column)
        }
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
        return "\n" + data.joinToString("\n") { it.joinToString(" | ") } + "\n"
    }

    override fun hashCode(): Int {
        return data.contentDeepHashCode()
    }
}

inline fun <reified T> A(vararg elements: T): Array<T> = arrayOf(*elements)