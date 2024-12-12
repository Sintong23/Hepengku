package com.kelompoksigma.hepengku_

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentHomeBinding
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Simulasi data transaksi
    private val transactions = listOf(
        Transaction(type = "income", amount = 5000.0),
        Transaction(type = "expense", amount = 2000.0),
        Transaction(type = "income", amount = 3000.0),
        Transaction(type = "expense", amount = 1000.0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi ke fragment_calender_detail saat imageView5 ditekan
        binding.imageView5.setOnClickListener {
            findNavController().navigate(R.id.calenderDetailFragment)
        }

        //redirect ke halaman detail_static
        binding.nilaiExpensee.setOnClickListener {
            findNavController().navigate(R.id.detail_statistic)
        }

        binding.nilaiIncomee.setOnClickListener {
            findNavController().navigate(R.id.detail_statistic)
        }

        binding.nilaiBalancee.setOnClickListener {
            findNavController().navigate(R.id.detail_statistic)
        }

        //redirect ke halaman detail
        binding.view3.setOnClickListener {
            findNavController().navigate(R.id.detailFragment)
        }

//        // Perhitungan income, expense, dan balance
//        val totalIncome = transactions.filter { it.type == "income" }.sumOf { it.amount }
//        val totalExpense = transactions.filter { it.type == "expense" }.sumOf { it.amount }
//        val balance = totalIncome - totalExpense
//
//        // Tampilkan hasil di tampilan
//        binding.tvIncome.text = "Income: Rp$totalIncome"
//        binding.tvExpanses.text = "Expense: Rp$totalExpense"
//        binding.tvBalance.text = "Balance: Rp$balance"

        // Ambil tanggal saat ini
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Akses TextView dari binding
        val tvMonth = binding.tvMonth

        // Buat TextView bisa ditekan untuk memilih bulan
        tvMonth.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, _ ->
                    val monthName = getMonthName(month)
                    tvMonth.text = "$monthName $year"
                },
                currentYear, currentMonth, currentDay
            )
            datePickerDialog.show()
        }
    }

    private fun getMonthName(month: Int): String {
        val months = arrayOf(
            "Januari", "Februari", "Maret",
            "April", "Mei", "Juni",
            "Juli", "Agustus", "September",
            "Oktober", "November", "Desember"
        )
        return months[month]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Data class untuk transaksi
    data class Transaction(val type: String, val amount: Double)
}
