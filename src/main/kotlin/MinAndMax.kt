/**
 * Finds the minimum and maximum values of an array using only ~3n/2 comparisons.
 * Instead of comparing each element twice, we can compare the elements in pairs:
 * (1) two elements with each other, (2) the smaller element to min, and (3) the
 * larger element to max.
 * @return Pair(min, max)
 */
fun <T: Comparable<T>> getMinAndMax(array: Array<T>): Pair<T, T> {
    var min = array[0]
    var max = array[0]
    if (array.size % 2 == 0) {
        for (i in 1..array.size - 3 step 2)
            if (array[i] > array[i + 1]) {
                if (array[i] > max)
                    max = array[i]
                if (array[i + 1] < min)
                    min = array[i + 1]
            } else {
                if (array[i + 1] > max)
                    max = array[i + 1]
                if (array[i] < min)
                    min = array[i]
            }
        if(array[array.size - 1] > max)
            max = array[array.size - 1]
        if(array[array.size - 1] < min)
            min = array[array.size - 1]
    } else {
        for(i in 1..array.size - 2 step 2)
            if (array[i] > array[i + 1]) {
                if (array[i] > max)
                    max = array[i]
                if (array[i + 1] < min)
                    min = array[i + 1]
            } else {
                if (array[i + 1] > max)
                    max = array[i + 1]
                if (array[i] < min)
                    min = array[i]
            }
    }
    return Pair(min, max)
}

fun main() {
    println(getMinAndMax(arrayOf(-12, 7, 1, 3, 11, 8, 0, -2, 10)))
}