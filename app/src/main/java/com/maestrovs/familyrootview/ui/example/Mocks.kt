package com.maestrovs.familyrootview.ui.example


val mockPerson = Person(
    "0",
    "Maria",
    father = Person(id = "1"),
    mother = Person(id = "2"),
)


val mockFather = Person(
    "1",
    "Father",
    )

val mockMother = Person(
    "2",
    "Mother",
    father = Person(id = "3"),
    mother = Person(id = "4"),
    )

val mockGrannyMom1 = Person(
    "3",
    "Granny Mom",
)

val mockGrannyDad1 = Person(
    "4",
    "Granny Dad",
)


val listOfPersons = listOf(
    mockPerson,
    mockFather, mockMother,
    mockGrannyDad1, mockGrannyMom1
)
