package com.kelompoksigma.hepengku_.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelompoksigma.hepengku_.retrovit.RetrofitInstance
import com.kelompoksigma.hepengku_.retrovit.SummaryResponse
import com.kelompoksigma.hepengku_.retrovit.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    // LiveData untuk menyimpan data dari API
    private val _summaryData = MutableLiveData<SummaryResponse>()
    val summaryData: LiveData<SummaryResponse> get() = _summaryData

    // LiveData untuk menyimpan pesan error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Fungsi untuk fetch data dari Retrofit
    fun fetchSummaryData() {
        RetrofitInstance.api.getSummary().enqueue(object : Callback<SummaryResponse> {
            override fun onResponse(call: Call<SummaryResponse>, response: Response<SummaryResponse>) {
                if (response.isSuccessful) {
                    Log.d("HomeViewModel", "Data summary berhasil: ${response.body()}")
                    _summaryData.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.message()}")
                    Log.e("HomeViewModel", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                Log.e("HomeViewModel", "Failure: ${t.message}")
                _error.postValue("Failure: ${t.message}")
            }
        })
    }


    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> get() = _transactions

    // Fungsi untuk mengambil data transaksi dari API
    fun fetchTransactions() {
        RetrofitInstance.api.getTransactions().enqueue(object : Callback<List<Transaction>> {
            override fun onResponse(call: Call<List<Transaction>>, response: Response<List<Transaction>>) {
                if (response.isSuccessful) {
                    Log.d("HomeViewModel", "Transaksi berhasil: ${response.body()}")
                    _transactions.postValue(response.body()) // Set nilai transaksi
                }
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                // Log error jika terjadi kegagalan
                Log.e("HomeViewModel", "Failure transaksi: ${t.message}")
                t.printStackTrace()
            }
        })
    }





}