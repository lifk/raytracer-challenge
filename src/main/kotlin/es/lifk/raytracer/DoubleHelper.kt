package es.lifk.raytracer

import kotlin.math.abs

const val EPSILON = 0.00001

fun Double.equal(other: Double) = abs(this - other) < EPSILON