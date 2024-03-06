package dev.trip.airflight.common.utils


sealed class ResultResource<out T> {
    data class Success<out T>(val result: T?, val message: String? = "") : ResultResource<T>()
    data class Failure(val throwable: Throwable) : ResultResource<Nothing>()
}