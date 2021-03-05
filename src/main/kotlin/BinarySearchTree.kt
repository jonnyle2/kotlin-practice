import java.util.Stack
import java.util.LinkedList

class BinarySearchTree<E: Comparable<E>>(c: Collection<E> = emptyList()): MutableCollection<E> {
    private var root: Node<E>? = null
    private var _size = 0

    init {
        if(c.isNotEmpty())
            addAll(c)
    }

    fun minOrNull(): E? {
        var node = root
        while(node?.l != null)
            node = node.l
        return node?.key
    }

    fun maxOrNull(): E? {
        var node = root
        while(node?.r != null)
            node = node.r
        return node?.key
    }

    override val size: Int
        get() = _size

    override fun contains(element: E): Boolean {
        var node = root
        while(node != null) {
            with(node) {
                node = when {
                    key == element -> return true
                    key > element -> l
                    else -> r
                }
            }
        }
        return false
    }

    override fun containsAll(elements: Collection<E>) = elements.all { contains(it) }

    override fun isEmpty() = size == 0

    override fun add(element: E): Boolean {
        var currentNode = root
        var parentNode: Node<E>? = null
        while(currentNode != null) {
            parentNode = currentNode
            with(currentNode) {
                currentNode = when {
                    key > element -> l
                    else -> r
                }
            }
        }
        with(parentNode) {
            when {
                this == null -> root = Node(element)    // empty tree
                key > element -> this.l = Node(element, this)
                else -> this.r = Node(element, this)
            }
        }
        _size++
        return true
    }

    override fun addAll(elements: Collection<E>) = elements.map { add(it) }.any { it }

    override fun clear() {
        root = null
    }

    override fun iterator(): MutableIterator<E> {
        TODO("Not yet implemented")
    }

    override fun remove(element: E): Boolean {
        /**
         * Local function replaces node "parent" with its child node "child"
         * appropriately as its parent's parent (grandparent's) left or right child.
         * ~sounds kind of weird
         */
        fun transplant(parent: Node<E>, child: Node<E>) {
            if()
        }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    fun printInOrder() {
        if(root == null)
            return
        val stack = Stack<Node<E>>()
        stack.push(root)
        var node = root?.l
        while(stack.isNotEmpty() || node != null) {
            while(node != null) {
                stack.push(node)
                node = node.l
            }
            node = stack.pop()
            print("${node.key} ")
            node = node.r
        }
        println()
    }

    fun printPreorder() {
        val stack = Stack<Node<E>>()
        stack.push(root)
        while(stack.isNotEmpty()) {
            with(stack.pop() ?: continue) {
                print("$key ")
                stack.push(r)
                stack.push(l)
            }
        }
        println()
    }

    fun printPostorder() {
        val stack = Stack<Node<E>>()
        val reverseStack = Stack<Node<E>>()
        stack.push(root)
        while(stack.isNotEmpty()) {
            with(stack.pop() ?: continue) {
                reverseStack.push(this)
                stack.push(l)
                stack.push(r)
            }
        }
        while(reverseStack.isNotEmpty())
            print("${reverseStack.pop().key} ")
        println()
    }

    fun printBreadthFirst() {
        val queue = LinkedList<Node<E>?>()
        queue.add(root)
        while(queue.isNotEmpty()) {
            with(queue.removeLast() ?: continue) {
                print("$key ")
                queue.addFirst(l)
                queue.addFirst(r)
            }
        }
        println()
    }

    private class Node<E>(var key: E,
                          var p: Node<E>? = null,
                          var l: Node<E>? = null,
                          var r: Node<E>? = null)
}

fun main() {
    val list = listOf(2, 5, 7, 6, 5, 8)
    val bst = BinarySearchTree(list)
    bst.printPreorder()
    bst.printPostorder()
    bst.printInOrder()
    bst.printBreadthFirst()
    println("Min: ${bst.minOrNull()}")
    println("Max: ${bst.maxOrNull()}")
}