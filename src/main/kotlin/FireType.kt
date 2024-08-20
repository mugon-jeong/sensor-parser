package org.example

import kotlin.reflect.KClass

sealed interface SensorType {
    val value: Int
}

class SensorTypeParser(private val kClass: KClass<out SensorType>) {
    // 각 SensorType을 매핑하는 맵
    fun parse(value: Int): Any {
        // 주어진 값에 해당하는 enum 상수를 찾음
        return kClass.java.enumConstants.firstOrNull { it.value == value }
            ?: throw IllegalArgumentException("Unsupported value: $value for type: $kClass")
    }

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