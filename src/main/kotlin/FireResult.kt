package org.example

data class FireResult(
    @SensorField(order = 0, size = 1, description = "message type")
    val messageType: FireType,
    @SensorField(order = 1, size = 1, description = "fire detector status")
    val fireDetector: Boolean,
    @SensorField(order = 2, size = 2, description = "fire detector voltage")
    val voltage: Int,
    @SensorField(order = 3, size = 1, description = "battery level")
    val battery: Int,
)
