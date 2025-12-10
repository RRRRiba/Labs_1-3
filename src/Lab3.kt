package src

fun main() {
    val puppy = Dog(8, true)
    val kitten = Cat(3, true)
    val goldfish = Fish(1, false)

    puppy.makeSound()
    kitten.makeSound()
    goldfish.makeSound()
}

abstract class LivingCreature {
    abstract val yearsOld: Int
    abstract val hasFur: Boolean
    abstract fun makeSound()
}

class Dog(
    override val yearsOld: Int,
    override val hasFur: Boolean
) : LivingCreature() {
    override fun makeSound() {
        println("Гав-гав!")
    }

    init {
        println("Собака возрастом $yearsOld лет")
    }
}

class Cat(
    override val yearsOld: Int,
    override val hasFur: Boolean
) : LivingCreature() {
    override fun makeSound() {
        println("Мяу!")
    }

    init {
        println("Кошка возрастом $yearsOld лет")
    }
}

class Fish(
    override val yearsOld: Int,
    override val hasFur: Boolean
) : LivingCreature() {
    override fun makeSound() {
        println("... (рыбы не издают звуков)")
    }

    init {
        println("Рыба возрастом $yearsOld год(а)")
    }
}