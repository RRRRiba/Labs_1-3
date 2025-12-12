package src

fun main() {
    val num1 = 22
    val num2 = 25

    println(addition(num1, num2))
    println(addition(num1, num2))
    println(subtraction(num1, num2))
    println(multiplication(num1, num2))
    println(division(num1, num2))
}

fun addition(firstValue: Int, secondValue: Int): Int = firstValue + secondValue

fun subtraction(firstValue: Int, secondValue: Int): Int = firstValue - secondValue

fun multiplication(firstValue: Int, secondValue: Int): Int = firstValue * secondValue

fun division(firstValue: Int, secondValue: Int): Int {
    require(secondValue != 0) { "Невозможно выполнить деление на ноль" }
    return firstValue / secondValue
}
