package org.example

fun main() {
    println("Hello World!")
    val parser = SensorParser(FireResult::class)
    val result = parser.parse(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08))
    println("Result: $result")
}