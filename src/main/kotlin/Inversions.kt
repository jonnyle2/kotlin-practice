/**
 * Calculates the total number of inversions of size 3 from the given
 * array. An inversion is some values where p&#91;i&#93; > p&#91;j&#93; > p&#91;k&#93; and
 * i < j < k in array p. This algorithm finds inversions by getting all
 * greater elements to the left-hand-side of a middle element, all
 * lesser elements to the right-hand-side, and multiplying the count on
 * each side. Time complexity: O(n^2)
 */
fun <T: Comparable<T>> countInversions(list: List<T>): Long {
    var count  = 0L
    for(j in 1..list.size - 2) {
        var leftCount = 0L
        var rightCount = 0L
        for(i in 0 until j)
            if(list[i] > list[j])
                leftCount++
        for(k in j + 1 until list.size)
            if(list[j] > list[k])
                rightCount++
        count += leftCount * rightCount
    }
    return count
}

fun main() {
    println(countInversions(listOf(8, 4, 2, 1)))
}