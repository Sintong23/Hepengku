package com.kelompoksigma.hepengku_.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelompoksigma.hepengku_.retrovit.RetrofitInstance
import com.kelompoksigma.hepengku_.retrovit.SummaryResponse
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
                    _summaryData.postValue(response.body())
                } else {
                    _error.postValue("Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                _error.postValue("Failure: ${t.message}")
            }
        })
    }
}