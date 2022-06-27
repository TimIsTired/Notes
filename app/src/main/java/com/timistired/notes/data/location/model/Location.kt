package com.timistired.notes.data.location.model

/**
 * Represents a geo position with latitude and longitude.
 * */
data class Location(val latitude: Double, val longitude: Double) {
    override fun toString(): String = "$latitude, $longitude"
}
