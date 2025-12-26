import kotlin.random.Random

open class AquariumCreature(
    val name: String,
    val color: String,
    var health: Int = 100
) {
    open fun makeSound() {
        println("$name издает звук")
    }

    open fun move() {
        println("$name перемещается")
    }

    fun reduceHealth(amount: Int) {
        health -= amount
        if (health < 0) health = 0
    }
}

class AquariumPlant(
    name: String,
    color: String,
    health: Int = 100
) : AquariumCreature(name, color, health) {

    override fun makeSound() {
        println("$name тихо шелестит")
    }

    override fun move() {
        println("$name покачивается в воде")
    }
}

fun AquariumCreature.isHealthy() = health > 70

fun AquariumCreature.isColor(expectedColor: String) = color.lowercase() == expectedColor.lowercase()

fun AquariumCreature.heal(amount: Int) {
    health += amount
    if (health > 100) health = 100
    println("$name вылечен на $amount единиц. Теперь здоровье: $health")
}

val AquariumCreature.status: String
    get() = when {
        health > 80 -> "Отличное"
        health > 50 -> "Хорошее"
        health > 20 -> "Среднее"
        else -> "Плохое"
    }

fun AquariumCreature?.interact() {
    this?.apply {
        println("Вы взаимодействуете с $name")
        makeSound()
        move()
    } ?: println("Здесь ничего нет")
}

fun AquariumPlant.producesOxygen() = color.lowercase() == "green"

val AquariumPlant.plantType: String
    get() = when {
        name.contains("водоросл", ignoreCase = true) -> "Водоросли"
        name.contains("коралл", ignoreCase = true) -> "Кораллы"
        else -> "Другое растение"
    }

class Fish(
    name: String,
    color: String,
    private val species: String,
    health: Int = 100
) : AquariumCreature(name, color, health) {

    override fun makeSound() {
        println("$name ($species) пускает пузыри")
    }

    override fun move() {
        println("$name плавает в толще воды")
    }
}

class Snail(
    name: String,
    color: String,
    health: Int = 100
) : AquariumCreature(name, color, health) {

    override fun makeSound() {
        println("$name тихо скребется")
    }

    override fun move() {
        println("$name медленно ползет по стеклу")
    }
}

open class Aquarium(val name: String, val capacity: Int, open var length: Int = 100, open var width: Int = 20, open var height: Int = 40) {
    private val inhabitants = mutableListOf<AquariumCreature>()
    private var cleanliness: Int = 100
    open val shape = "прямоугольный"

    open var volume: Int
        get() = width * height * length / 1000
        set(value) {
            height = (value * 1000) / (width * length)
        }

    open val water: Double
        get() = volume * 0.9

    fun printSize() {
        println("Ширина: $width см " +
                "Длина: $length см " +
                "Высота: $height см ")

        println("Объем: $volume литров; Воды: $water литров (${water / volume * 100.0}% заполненность)")
        println("Аквариум $shape формы")
    }

    fun addInhabitant(creature: AquariumCreature): Boolean {
        return if (inhabitants.size < capacity) {
            inhabitants.add(creature)
            println("${creature.name} добавлен(а) в аквариум '$name'")
            true
        } else {
            println("Аквариум '$name' переполнен! Нельзя добавить ${creature.name}")
            false
        }
    }

    fun simulateDay() {
        println("\n=== Симуляция дня в аквариуме '$name' ===")

        inhabitants.forEach { creature ->
            creature.move()

            when (Random.nextInt(10)) {
                0 -> {
                    creature.reduceHealth(30)
                    println("${creature.name} поранился(ась). Здоровье: ${creature.health}")
                }
                1 -> {
                    creature.heal(5)
                }
            }
        }

        cleanliness -= inhabitants.size * 2
        if (cleanliness < 0) cleanliness = 0

        println("Чистота аквариума: $cleanliness%")
        println("Всего обитателей: ${inhabitants.size}/${capacity}")
    }

    fun showStatus() {
        println("\n=== Статус аквариума '$name' ===")
        println("Чистота: $cleanliness%")
        println("Заполненность: ${inhabitants.size}/$capacity")

        inhabitants.forEach { creature ->
            val symbol = when (creature) {
                is Fish -> "🐟"
                is Snail -> "🐌"
                is AquariumPlant -> "🌿"
                else -> "❓"
            }
            println("$symbol ${creature.name} (${creature.color}) - здоровье: ${creature.status}")
        }
    }

    fun clean() {
        cleanliness = 100
        println("Аквариум '$name' почищен!")
    }
}

class TowerTank (override var height: Int, var diameter: Int): Aquarium(height = height, width = diameter, length = diameter, name = "Cool", capacity = 100) {
    override var volume: Int
        get() = (width/2 * length/2 * height / 1000 * 3).toInt()
        set(value) {
            height = ((value * 1000 / 3) / (width/2 * length/2)).toInt()
        }
    override var water = volume * 0.8
    override val shape = "цилиндровой"
}

fun List<AquariumCreature>.findAllByColor(color: String): List<AquariumCreature> {
    return this.filter { it.isColor(color) }
}

fun List<AquariumCreature>.countHealthy(): Int {
    return this.count { it.isHealthy() }
}

fun main() {
    val myAquarium = Aquarium("Морские глубины", 10, 50, 50, 50)
    val myAquarium1 = TowerTank (100, 50)

    myAquarium.printSize()
    println()
    myAquarium1.printSize()
    println()

    val nemo = Fish("Немо", "оранжевый", "клоун")
    val dory = Fish("Дори", "синий", "хирург")
    val sheldon = Snail("Шелдон", "коричневый")
    val algae = AquariumPlant("Водоросли", "зеленый", 15)
    val coral = AquariumPlant("Коралл", "красный", 8)

    myAquarium.addInhabitant(nemo)
    myAquarium.addInhabitant(dory)
    myAquarium.addInhabitant(sheldon)
    myAquarium.addInhabitant(algae)
    myAquarium.addInhabitant(coral)

    println("\n=== Демонстрация расширений ===")

    println("Немо здоров? ${nemo.isHealthy()}")
    println("Водоросли производят кислород? ${algae.producesOxygen()}")
    println("Тип водорослей: ${algae.plantType}")
    println("Статус здоровья Дори: ${dory.status}")

    var unknownCreature: AquariumCreature? = null
    unknownCreature.interact()

    unknownCreature = nemo
    unknownCreature.interact()

    val creature: AquariumCreature = coral
    println("\nДемонстрация статического разрешения:")
    println("creature.isColor(\"красный\"): ${creature.isColor("красный")}")

    repeat(3) { day ->
        println("\n\nДень ${day + 1}:")
        myAquarium.simulateDay()
        myAquarium.showStatus()

        if (day == 1) {
            myAquarium.clean()
        }
    }

    println("\n=== Анализ обитателей ===")
    val allInhabitants = listOf(nemo, dory, sheldon, algae, coral)
    val redInhabitants = allInhabitants.findAllByColor("красный")
    println("Красных обитателей: ${redInhabitants.size}")
    println("Здоровых обитателей: ${allInhabitants.countHealthy()}")

    println("\n=== Лечение обитателей ===")
    val woundedCreatures = allInhabitants.filter { !it.isHealthy() }

    if (woundedCreatures.isEmpty()) {
        println("Все существа здоровы! Лечение не требуется.")
    } else {
        println("Найдено раненых существ: ${woundedCreatures.size}")

        woundedCreatures.forEach { creature ->
            val healthToAdd = when {
                creature.health < 30 -> 40
                creature.health < 50 -> 30
                else -> 20
            }

            println("Лечим ${creature.name} (текущее здоровье: ${creature.health})...")
            creature.heal(healthToAdd)
        }

        println("Лечение завершено!")
    }

    println("\n=== Финальный статус ===")
    myAquarium.showStatus()

}