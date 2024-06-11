# FamilyRootView

**FamilyRootView** is a Compose view component that takes a list of relatives containing references to their parents and automatically draws a family relationship diagram. This is the first version of the component.

## Features

- **Automatic Diagram Generation**: Automatically creates a visual representation of family relationships.
- **Interactive**: Allows for interactive elements such as clickable persons.

## Getting Started

### 1. Add the Dependency

Add the following to your `build.gradle` file:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.maestrovsmaster:FamilyRootView:1.0.0'
}
```

### 2. Define Your Data Model
Define your Person data class:

```groovy
data class Person(
    override var id: String = "",
    override var name: String = "",
    override var mother: Person? = null,
    override var father: Person? = null,
    override var children: List<Person>? = null,
    override var siblings: List<Person>? = null,
) : AbstractPerson<Person>(id, name, mother, father) {
    override fun description() = name
}
```

### 3. Create Sample Data
Create your sample data:
```groovy
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
...

val listOfPersons = listOf(
    mockPerson,
    mockFather, mockMother,
    mockGrannyDad1, mockGrannyMom1
)

val peopleMap = listOfPersons.associateBy { it.id ?: "null" }
val personId = "0"
```

### 4. Build the Family Tree
Build the family tree and render the diagram:

```groovy
val rootPerson = peopleMap[personId]
rootPerson?.let {
    val binaryTree = buildBinaryTree(it, peopleMap)
    
    FamilyRootCanvas(sizeDp, binaryTree) { person ->
        Log.d("FamilyRootView", "ClickedPerson = $person")
    }
}

```

## Screenshots

<img width="663" alt="image" src="https://github.com/maestrovsmaster/FamilyRootView/assets/23098862/89461b8c-96c0-4990-9945-e1623daa88a9">


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
