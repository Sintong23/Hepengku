package com.kelompoksigma.hepengku_.retrovit
import retrofit2.Call
import retrofit2.http.GET

data class SummaryResponse(
    val income: Int,
    val expense: Int,
    val balance: Int


)

data class Transaction(
    val date: String,          // Format tanggal: "2023-12-11"
    val category_name: String, // Nama kategori: "Makan"
    val amount: Int,           // Nominal transaksi: 100000
    val icon: String,           // Nama drawable: "ic_food"
    val type: String,
    val note: String
)

interface ApiService {
    @GET("api/summary")
    fun getSummary(): Call<SummaryResponse>

    @GET("api/transactions")
    fun getTransactions(): Call<List<Transaction>>
}
