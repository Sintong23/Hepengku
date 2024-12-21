package com.kelompoksigma.hepengku_.retrovit
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

data class Category(
    val id: Int,
    val name: String,
    val icon: String, // Nama file ikon dari database
    val type: String // Tambahkan tipe kategori
)


data class ExpenseSummary(
    val category_name: String,
    val percentage: Float
)

data class IncomeSummary(
    val category_name: String,
    val percentage: Float
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

    @GET("api/categories")
    suspend fun getCategories(): List<Category>

    @FormUrlEncoded
    @POST("api/transactions")
    suspend fun addTransaction(
        @Field("date") date: String,
        @Field("amount") amount: Int,
        @Field("type") type: String,
        @Field("category_id") categoryId: Int,
        @Field("note") note: String
    ): Response<Unit>

    @DELETE("api/transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Response<Unit>

    @GET("api/transactions/expense-summary")
    suspend fun getExpenseSummary(): Response<List<ExpenseSummary>>

    @GET("api/transactions/income-summary")
    suspend fun getIncomeSummary(): Response<List<IncomeSummary>>

}
