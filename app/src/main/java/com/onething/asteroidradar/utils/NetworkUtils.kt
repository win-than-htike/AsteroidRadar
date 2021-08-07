package com.onething.asteroidradar.utils

import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

suspend fun <T> getResponse(retrofit: Retrofit, request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
    return try {
        println("I'm working in thread ${Thread.currentThread().name}")
        val result = request.invoke()
        if (result.isSuccessful) {
            return Result.success(result.body())
        } else {
            val errorResponse = ErrorUtils.parseError(result, retrofit)
            Result.error(errorResponse?.localizedMessage ?: defaultErrorMessage, errorResponse)
        }
    } catch (e: Throwable) {
        Result.error("Unknown Error", null)
    }
}

fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()
    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return formattedDateList
}

fun getToday(): String {
    val calendar = Calendar.getInstance()
    return toFormatDate(calendar.time)
}

fun getSevenDayTodayOnwards(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return toFormatDate(calendar.time)
}

private fun toFormatDate(date: Date): String {
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.ENGLISH)
    return dateFormat.format(date)
}