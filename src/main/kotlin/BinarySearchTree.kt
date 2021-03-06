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

    private fun search(element: E): Node<E>? {
        var node = root
        while(node != null) {
            with(node) {
                node = when {
                    key == element -> return this
                    key > element -> l
                    else -> r
                }
            }
        }
        return null
    }

    override fun contains(element: E): Boolean {
        search(element) ?: return false
        return true
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
        return object : MutableIterator<E> {
            val stack = Stack<Node<E>>()
            var last: Node<E>? = null
            init {
                var node = root
                while(node != null) {
                    stack.push(node)
                    node = node.l
                }
            }

            override fun hasNext() = stack.size > 0

            override fun next(): E {
                last = stack.pop()
                var node = last?.r
                while(node != null) {
                    stack.push(node)
                    node = node?.l
                }
                return last?.key ?: throw Exception()
            }

            override fun remove() {
                remove(last?.key)
            }
        }
    }

    override fun remove(element: E): Boolean {
        /**
         * Local function replaces node "parent" with its child node "child"
         * appropriately as its parent's parent (grandparent's) left or right child.
         * ~sounds kind of weird
         */
        fun transplant(parent: Node<E>?, child: Node<E>?) {
            when {
                parent?.p == null -> root = child
                parent.p?.l == parent -> parent.p?.l = child
                else -> parent.p?.r = child
            }
            child?.p = parent?.p
        }

        val node = search(element) ?: return false
        when {
            node.l == null -> transplant(node, node.r)
            node.r == null -> transplant(node, node.l)
            else -> {
                var leftChild = node.r
                while(leftChild?.l != null) {
                    leftChild = leftChild.l
                }
                if(leftChild?.p != node) {
                    transplant(leftChild, leftChild?.r)
                    leftChild?.r = node.r
                    leftChild?.r?.p = leftChild
                }
                transplant(node, leftChild)
                leftChild?.l = node.l
                leftChild?.l?.p = leftChild
            }
        }
        _size--
        return true
    }

    override fun removeAll(elements: Collection<E>) = elements.map { remove(it) }.any { it }

    override fun retainAll(elements: Collection<E>): Boolean {
        var changed = false
        with(iterator()) {
            while(hasNext())
                if(!elements.contains(next())) {
                    remove()
                    changed = true
            }
        }
        return changed
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
    bst.printInOrder()
    bst.retainAll(listOf(2, 4, 5, 8))
    bst.printInOrder()
}