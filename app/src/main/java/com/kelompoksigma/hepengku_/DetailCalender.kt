package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksigma.hepengku_.adapter.DetailTransactionAdapter
import com.kelompoksigma.hepengku_.databinding.FragmentDetailCalenderBinding
import com.kelompoksigma.hepengku_.retrovit.RetrofitInstance
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailCalender : Fragment() {

    private var _binding: FragmentDetailCalenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil tanggal dari argumen
        val rawDate = arguments?.getString("selectedDate") ?: ""

        // Format ulang jika diperlukan (opsional)
        val selectedDate = try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(rawDate)
            )
        } catch (e: Exception) {
            rawDate // Jika gagal, gunakan rawDate
        }

        binding.tvTanggal.text = selectedDate

        // Ambil data transaksi berdasarkan tanggal
        loadTransactionData(selectedDate)
    }

    private fun loadTransactionData(date: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getTransactionsByDate(date)
                if (response.isSuccessful) {
                    val data = response.body()
                    val transactions = data?.transactions ?: emptyList()
                    val totalIncome = data?.total_income ?: 0
                    val totalExpense = data?.total_expense ?: 0

                    // Update income and expense
                    binding.totalIncome.text = "Income: Rp ${String.format("%,d", totalIncome)}"
                    binding.totalExpense.text = "Expense: Rp ${String.format("%,d", totalExpense)}"

                    // Setup RecyclerView
                    binding.recyclerViewTransactions.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.recyclerViewTransactions.adapter =
                        DetailTransactionAdapter(requireContext(), transactions)
                } else {
                    Log.e("DetailCalender", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DetailCalender", "Error: ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}