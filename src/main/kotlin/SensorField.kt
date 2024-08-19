package org.example

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class SensorField(
    val order: Int,
    val size: Int,
    val description: String
)
