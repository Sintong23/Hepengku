package com.kelompoksigma.hepengku_.retrovit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Ganti URL ini dengan alamat server backend Laravel Anda
    private const val BASE_URL = "http://10.0.2.2:8000/" // Gunakan 10.0.2.2 jika menggunakan emulator Android

    // Retrofit instance
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Base URL dari server backend
            .addConverterFactory(GsonConverterFactory.create()) // Konverter JSON menggunakan Gson
            .build()
            .create(ApiService::class.java) // Menghubungkan dengan ApiService
    }
}

