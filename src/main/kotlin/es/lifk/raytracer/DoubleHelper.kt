package es.lifk.raytracer

import kotlin.math.abs

fun Double.equal(other: Double) = abs(this - other) < 0.00001