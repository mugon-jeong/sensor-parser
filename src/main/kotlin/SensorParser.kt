package org.example

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.superclasses

class SensorParser<T : Any>(private val kClass: KClass<T>) {
    fun parse(data: ByteArray): T {
        println("Parsing data as ${kClass.simpleName}")

        val constructor = kClass.primaryConstructor ?: throw Exception("No primary constructor")
        val sortedParameters = constructor.parameters.mapNotNull { property ->
            val sensorFieldAnnotation = property.findAnnotation<SensorField>()
            if (sensorFieldAnnotation != null) {
                property to sensorFieldAnnotation
            } else {
                null
            }
        }.sortedBy { it.second.order }

        // 정렬된 필드를 기반으로 인자를 구성
        val args = mutableMapOf<KParameter, Any>()
        var offset = 0

        for ((parameter, annotation) in sortedParameters) {
            println("Parameter: ${parameter.name}, Type: ${parameter.type}")
            println("Order: ${annotation.order}, Size: ${annotation.size}, Description: ${annotation.description}")

            // 데이터를 annotation.size만큼 자름
            val valueAsInt = data.sliceArray(offset until offset + annotation.size).toInt()

            // 자른 데이터를 실제로 파싱하여 각 필드의 값을 추출
            val parsedValue: Any = when (val type = parameter.type.classifier) {
//                FireType::class -> FireType.entries.firstOrNull { it.value == valueAsInt } ?: FireType.UNKNOWN // Enum 타입일 경우
                Boolean::class -> valueAsInt != 0 // Boolean 타입일 경우
                Int::class -> valueAsInt // Int 타입일 경우
                is KClass<*> -> {
                    if (type.superclasses.contains(SensorType::class)) {
                        val sensorTypeParser = SensorTypeParser(type as KClass<out SensorType>)
                        sensorTypeParser.parse(valueAsInt)
                    } else {
                        throw IllegalArgumentException("Unsupported type: $type")
                    }
                }

                else -> {
                    throw IllegalArgumentException("Unsupported type: $type")
                }
            }

            args[parameter] = parsedValue
            offset += annotation.size
        }

        // 생성자에 인자를 전달하여 객체 생성
        return constructor.callBy(args)
    }
}

// ByteArray에서 특정 범위를 Int로 변환하는 확장 함수
fun ByteArray.toInt(): Int {
    return this.fold(0) { acc, byte -> (acc shl 8) or (byte.toInt() and 0xFF) }
}