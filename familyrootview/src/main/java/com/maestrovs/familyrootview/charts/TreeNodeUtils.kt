package com.maestrovs.familyrootview.charts

import com.maestrovs.familyrootview.data.AbstractPerson


//data class TreeNode(val person: AbstractPerson, var left: TreeNode? = null, var right: TreeNode? = null)

data class TreeNode<T : AbstractPerson<T>>(val person: T, var left: TreeNode<T>? = null, var right: TreeNode<T>? = null){

    fun measureDeep(): Int {
        val leftDepth = left?.measureDeep() ?: 0
        val rightDepth = right?.measureDeep() ?: 0

        return 1 + maxOf(leftDepth, rightDepth)
    }

}

fun <T : AbstractPerson<T>> buildBinaryTree(person: T, peopleMap: Map<String, T>): TreeNode<T> {
    val node = TreeNode(person)

    // Temporary empty parent
    val temporaryEmptyParent = object : AbstractPerson<T>("", "") {
        override fun description(): String {
            return "?"
        }
    }

    person.mother?.id?.let { motherId ->
        peopleMap[motherId]?.let { mother ->
            node.left = buildBinaryTree(mother, peopleMap)
        }
    } ?: run {
        node.left = TreeNode(temporaryEmptyParent as T)
    }

    person.father?.id?.let { fatherId ->
        peopleMap[fatherId]?.let { father ->
            node.right = buildBinaryTree(father, peopleMap)
        }
    } ?: run {
        node.right = TreeNode(temporaryEmptyParent as T)
    }

    return node
}



/*

fun buildBinaryTree(person: AbstractPerson, peopleMap: Map<String, AbstractPerson>): TreeNode {
    val node = TreeNode(person)

    val temporaryEmptyParent = AbstractPerson(children = listOf(person.toPersonSummary()))

    person.mother()?.id()?.let { motherId ->
        peopleMap[motherId]?.let { mother ->
            node.left = buildBinaryTree(mother, peopleMap)
        }
    }?:run{
        node.left = TreeNode(temporaryEmptyParent)
    }

    person.father()?.personId?.let { fatherId ->
        peopleMap[fatherId]?.let { father ->
            node.right = buildBinaryTree(father, peopleMap)
        }
    }?:run{
        node.right = TreeNode(temporaryEmptyParent)
    }

    return node
}*/

