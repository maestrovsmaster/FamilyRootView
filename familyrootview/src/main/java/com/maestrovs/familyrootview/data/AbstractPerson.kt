package com.maestrovs.familyrootview.data

abstract class AbstractPerson<T>(
    open var id: String,
    open var name: String,
    open var mother: T? = null,
    open var father: T? = null,
    open var children: List<T>? = null,
    open var siblings: List<T>? = null,
){
    abstract fun description(): String
}
