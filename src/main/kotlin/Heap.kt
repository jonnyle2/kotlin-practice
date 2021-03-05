class Heap <T: Comparable<T>>(list: List<T> = listOf()): Collection<T> by list {
    private val heapArray: MutableList<T> = createHeapList(list)

    fun peek() = heapArray[0]

    fun poll(): T {
        val max = peek()
        heapArray.swap(0, heapArray.size - 1)
        heapArray.removeAt(heapArray.size - 1)
        heapify(heapArray, 0)
        return max
    }

    fun add(t: T) {
        heapArray.add(t)
        var i = heapArray.size - 1
        while(i > 0 && heapArray[i] > heapArray[(i - 1)/2]) {
            heapArray.swap(i, (i - 1)/2)
            i = (i - 1)/2
        }
    }

    override fun toString() = heapArray.toString()
}

fun <T: Comparable<T>> heapify(array: MutableList<T>, i: Int) {
    var largest = i
    if(i * 2 + 1 < array.size)
        if(array[i * 2 + 1] > array[i])
            largest = i * 2 + 1
    if(i * 2 + 2 < array.size)
        if(array[i * 2 + 2] > array[largest])
            largest = i * 2 + 2
    if(largest != i) {
        array.swap(i, largest)
        heapify(array, largest)
    }
}

fun <T: Comparable<T>> MutableList<T>.swap(a: Int, b: Int) {
    val temp = this[a]
    this[a] = this[b]
    this[b] = temp
}

fun <T: Comparable<T>> createHeapList(array: List<T>): MutableList<T> {
    val temp = array.toMutableList()
    for(i in (temp.size - 2)/2 downTo 0)
        heapify(temp, i)
    return temp
}

fun <T: Comparable<T>> heapSort(array: MutableList<T>) {
    val temp = Heap(array)
    for(i in array.size - 1 downTo 0)
        array[i] = temp.poll()
}

fun main() {
    val heap = Heap(mutableListOf(5, 2, 8, -2, 20))
    println(heap.isEmpty())
    heap.add(4)
    println(heap)
    heap.add(21)
    println(heap)
    val heap2 = Heap<Int>()
    heap2.add(5)
    heap2.add(2)
    heap2.add(8)
    heap2.add(-2)
    heap2.add(20)
    println(heap2.toString())
    val toSort = mutableListOf(5, 1, -2, 4, 9, 20, 40, 100, 21)
    println(toSort)
    heapSort(toSort)
    println(toSort)
}