package com.kelompoksigma.hepengku_.retrovit
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class SummaryResponse(
    val income: Int,
    val expense: Int,
    val balance: Int


)

data class Transaction(
    val id: Int,
    val date: String,          // Format tanggal: "2023-12-11"
    val category_name: String, // Nama kategori: "Makan"
    val amount: Int,           // Nominal transaksi: 100000
    val icon: String,           // Nama drawable: "ic_food"
    val type: String,
    val note: String
)

data class ApiResponse(
    val message: String,
    val transaction: Transaction
)

data class TransactionsByDateResponse(
    val transactions: List<Transaction>,
    val total_income: Int,
    val total_expense: Int
)


interface ApiService {
    @GET("api/summary")
    fun getSummary(): Call<SummaryResponse>

    @GET("api/transactions")
    fun getTransactions(): Call<List<Transaction>>

    @PUT("api/transactions/{id}/amount")
    suspend fun updateAmount(
        @Path("id") id: Int,
        @Body payload: Map<String, Int>
    ): Response<ApiResponse>

    @GET("api/transactions/date/{date}")
    suspend fun getTransactionsByDate(
        @Path("date") date: String
    ): Response<TransactionsByDateResponse>

}
