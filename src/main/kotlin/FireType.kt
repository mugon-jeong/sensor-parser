package org.example

sealed interface SensorType {
    val value: Int
}

enum class FireType(override val value: Int) : SensorType {
    HEART_BEAT(0),
    ALARM(1),
    TRIGGER_KEY_PRESSED(2),
    DEVICE_STATE_REQUEST(3),
    UNKNOWN(-1);
}

enum class GasType(override val value: Int) : SensorType {
    BEAT(0),
    ALARM(1),
    KEY_PRESSED(2),
    DEVICE_REQUEST(3),
    UNKNOWN(-1);
}