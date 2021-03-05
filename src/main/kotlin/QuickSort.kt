import kotlin.random.Random

fun <T> Array<T>.swap(a: Int, b: Int) {
    val temp = this[a]
    this[a] = this[b]
    this[b] = temp
}

fun <T: Comparable<T>> partition(array: Array<T>, p: Int, r: Int): Int {
    val x = array[r]
    var i = p - 1
    for(j in p until r)
        if(array[j] <= x) {
            i++
            array.swap(i, j)
        }
    array.swap(i + 1, r)
    return i + 1
}

fun <T: Comparable<T>> quicksort(array: Array<T>, p: Int, r: Int) {
    if(p < r) {
        val q = partition(array, p, r)
        quicksort(array, p, q - 1)
        quicksort(array, q + 1, r)
    }
}

fun <T: Comparable<T>> randomizedPartition(array: Array<T>, p: Int, r: Int): Int {
    val i = Random.nextInt(p, r + 1)
    array.swap(i, r)
    return partition(array, p, r)
}

fun <T: Comparable<T>> randomizedQuicksort(array: Array<T>, p: Int, r: Int) {
    if(p < r) {
        val q = randomizedPartition(array, p, r)
        randomizedQuicksort(array, p, q - 1)
        randomizedQuicksort(array, q + 1, r)
    }
}

fun <T: Comparable<T>> Array<T>.quicksort() = quicksort(this, 0, this.size - 1)

fun <T: Comparable<T>> Array<T>.randomQuicksort() = randomizedQuicksort(this, 0, this.size - 1)

fun main() {
    val size = 10000
    val array = (0 until size).map {Random.nextInt()}.toTypedArray()
    val array2 = arrayOf(*array)
    var start = System.nanoTime()
//    println(array.asList())
    array.quicksort()
    println("PivotLast: " + (System.nanoTime() - start))
//    println(array.asList())
//    println(array2.asList())
    start = System.nanoTime()
    array2.randomQuicksort()
    println("PivotRandom: " + (System.nanoTime() - start))
//    println(array2.asList())
}