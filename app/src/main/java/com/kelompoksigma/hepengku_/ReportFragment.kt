package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kelompoksigma.hepengku_.databinding.FragmentReportBinding
import com.kelompoksigma.hepengku_.retrovit.ApiService
import com.kelompoksigma.hepengku_.retrovit.SummaryResponse
import com.kelompoksigma.hepengku_.retrovit.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieChart: PieChart

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/") // Replace with your API URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
        initializePieChart()
        fetchSummaryData()
        fetchTransactionData()
    }

    private fun setupNavigation() {
        binding.imageView6.setOnClickListener {
            findNavController().navigate(R.id.detailmonthly)
        }
    }

    private fun initializePieChart() {
        pieChart = binding.pieChart
        pieChart.apply {
            description.isEnabled = false // Remove chart description
            isRotationEnabled = true
            setUsePercentValues(true)
        }
    }

    private fun fetchSummaryData() {
        apiService.getSummary().enqueue(object : Callback<SummaryResponse> {
            override fun onResponse(call: Call<SummaryResponse>, response: Response<SummaryResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { summary ->
                        binding.nilai.text = summary.expense.toString()
                        binding.nilai2.text = summary.income.toString()
                        binding.nilai3.text = summary.balance.toString()
                    }
                } else {
                    showToast("Gagal mendapatkan data summary")
                }
            }

            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
                showToast("Kesalahan jaringan: ${t.message}")
            }
        })
    }

    private fun fetchTransactionData() {
        apiService.getTransactions().enqueue(object : Callback<List<Transaction>> {
            override fun onResponse(call: Call<List<Transaction>>, response: Response<List<Transaction>>) {
                if (response.isSuccessful) {
                    response.body()?.let { transactions ->
                        setupPieChart(transactions)
                    }
                } else {
                    showToast("Gagal mendapatkan data transaksi")
                }
            }

            override fun onFailure(call: Call<List<Transaction>>, t: Throwable) {
                showToast("Kesalahan jaringan: ${t.message}")
            }
        })
    }

    private fun setupPieChart(transactions: List<Transaction>) {
        val entries = transactions
            .filter { it.type == "expense" }
            .groupBy { it.category_name }
            .map { (category, transactionList) ->
                PieEntry(transactionList.sumOf { it.amount }.toFloat(), category)
            }

        val pieDataSet = PieDataSet(entries, "Kategori Pengeluaran").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            setDrawValues(true)
        }

        val pieData = PieData(pieDataSet).apply {
            setValueTextSize(12f)
            setValueTextColor(android.graphics.Color.WHITE)
        }

        pieChart.data = pieData
        pieChart.invalidate()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
