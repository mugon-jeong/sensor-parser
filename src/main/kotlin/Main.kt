package org.example

import java.util.*

fun main() {
    println("Hello World!")
    val parser = SensorParser(FireResult::class)
    val test = "2hQAAF23ZA=="
    val result = parser.parse(base64ToByteArray(test).slice(2 until 7).toByteArray())
    println("Result: $result")
}

fun base64ToByteArray(base64: String): ByteArray {
    return Base64.getDecoder().decode(base64)
}