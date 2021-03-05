/**
 * Finds the ith smallest element in the array.
 * Uses random partitioning function from QuickSort.kt
 * Time complexity: Worst-case is theta(n^2),
 * expected-case is O(n)
 */
fun <T: Comparable<T>> randomSelect(array: Array<T>, i: Int): T? {
    fun <T: Comparable<T>> randomSelect(array: Array<T>, p: Int, r: Int, i: Int): T {
        if(p == r)
            return array[p]
        val q = randomizedPartition(array, p, r)
        val k = q - p + 1
        return when {
            i == k -> array[q]
            i < k -> randomSelect(array, p, q - 1, i)
            else -> randomSelect(array, q + 1, r, i - k)
        }
    }

    return when {
        i > array.size || i <= 0 -> null
        else -> randomSelect(array, 0, array.size - 1, i)
    }
}

fun main() {
    println(randomSelect(arrayOf(5, 2, 3, 6, 8), 1) ?: "Number is not in range of array.")
}