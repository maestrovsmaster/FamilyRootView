package com.maestrovs.familyrootview.ui.example

import com.maestrovs.familyrootview.data.AbstractPerson


data class Person(
    override var id: String = "",
    override var name: String = "",
    override var mother: Person? = null,
    override var father: Person? = null,
    override var children: List<Person>? = null,
    override var siblings : List<Person>? = null,
) : AbstractPerson<Person>(id, name, mother, father){
    override fun description() = name

}
