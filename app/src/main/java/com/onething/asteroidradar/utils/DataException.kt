package com.onething.asteroidradar.utils

sealed class DataException {
    class Api(val message: String) : DataException()
}