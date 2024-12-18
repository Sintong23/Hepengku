package com.kelompoksigma.hepengku_.retrovit
import retrofit2.Call
import retrofit2.http.GET

data class SummaryResponse(
    val income: Int,
    val expense: Int,
    val balance: Int
)

interface ApiService {
    @GET("api/summary")
    fun getSummary(): Call<SummaryResponse>
}