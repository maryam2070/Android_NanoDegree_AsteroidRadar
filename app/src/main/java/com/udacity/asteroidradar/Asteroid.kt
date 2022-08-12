package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroid_table")
data class AsteroidForDatabase(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean) : Parcelable


@Parcelize
data class Asteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean) : Parcelable

fun List<AsteroidForDatabase>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename=it.codename,
            closeApproachDate=it.closeApproachDate,
            absoluteMagnitude=it.absoluteMagnitude,
            estimatedDiameter=it.estimatedDiameter,
            relativeVelocity=it.relativeVelocity,
            distanceFromEarth=it.distanceFromEarth,
            isPotentiallyHazardous=it.isPotentiallyHazardous
        )
    }
}

/////////////////////
fun List<Asteroid>.asDatabaseModel(): List<AsteroidForDatabase> {
    return map {
        AsteroidForDatabase(
            id = it.id,
            codename=it.codename,
            closeApproachDate=it.closeApproachDate,
            absoluteMagnitude=it.absoluteMagnitude,
            estimatedDiameter=it.estimatedDiameter,
            relativeVelocity=it.relativeVelocity,
            distanceFromEarth=it.distanceFromEarth,
            isPotentiallyHazardous=it.isPotentiallyHazardous
        )
    }
}