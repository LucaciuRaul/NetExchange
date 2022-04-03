package net.exchange.app.data.model.entity

import java.util.*
import kotlin.collections.HashMap

data class ExchangeRate(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: Date,
    val rates: HashMap<String, Double>,
    val error: ApiError,
)

data class ApiError(
    val code: Int,
    val type: String,
    val info: String,
)
