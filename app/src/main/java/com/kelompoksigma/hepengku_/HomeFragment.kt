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
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoksigma.hepengku_.viewmodel.HomeViewModel
import com.kelompoksigma.hepengku_.adapter.TransactionAdapter




class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Inisialisasi ViewModel
    private val homeViewModel: HomeViewModel by viewModels()

//    // Simulasi data transaksi
//    private val transactions = listOf(
//        Transaction(type = "income", amount = 5000.0),
//        Transaction(type = "expense", amount = 2000.0),
//        Transaction(type = "income", amount = 3000.0),
//        Transaction(type = "expense", amount = 1000.0)
//    )


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

        // Observe LiveData untuk data summary
        homeViewModel.summaryData.observe(viewLifecycleOwner) { summary ->
            Log.d("HomeFragment", "Data dari API: Income=${summary.income}, Expense=${summary.expense}, Balance=${summary.balance}")
            binding.nilaiIncomee.text = "Rp ${summary.income}"
            binding.nilaiExpensee.text = "Rp ${summary.expense}"
            binding.nilaiBalancee.text = "Rp ${summary.balance}"
        }

        // Observe LiveData untuk error
        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.nilaiIncomee.text = errorMessage
//            binding.nilaiExpensee.text = errorMessage
//            binding.nilaiBalancee.text = errorMessage
        }

        // Observe LiveData untuk transaksi (RecyclerView)
        homeViewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            if (transactions.isNullOrEmpty()) {
                Log.e("HomeFragment", "Tidak ada transaksi yang diterima dari API")
            } else {
                Log.d("HomeFragment", "Transaksi berhasil dimuat: ${transactions.size} item")
                val groupedTransactions = transactions.groupBy { it.date }
                val adapter = TransactionAdapter(groupedTransactions)
                binding.recyclerViewTransactions.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerViewTransactions.adapter = adapter
            }
        }

        // Ambil data summary dan transaksi dari ViewModel
        homeViewModel.fetchSummaryData()
        Log.d("HomeFragment", "fetchSummaryData() dipanggil")

        homeViewModel.fetchTransactions()
        Log.d("HomeFragment", "fetchTransactions() dipanggil")



        // Navigasi ke fragment_calender_detail saat imageView5 ditekan
        binding.iconKalender.setOnClickListener {
            findNavController().navigate(R.id.CalenderDetailFragment) // Pastikan ID fragment di `main_nav.xml` sesuai
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
//        binding.imageView3.setOnClickListener {
//            findNavController().navigate(R.id.homeFragment)
//        }




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

//    private fun fetchSummaryData() {
//        RetrofitInstance.api.getSummary().enqueue(object : Callback<SummaryResponse> {
//            override fun onResponse(call: Call<SummaryResponse>, response: Response<SummaryResponse>) {
//                if (response.isSuccessful) {
//                    val summary = response.body()
//                    summary?.let {
//                        // Log respons untuk debugging
//                        Log.d("API_RESPONSE", "Income: ${it.income}, Expense: ${it.expense}, Balance: ${it.balance}")
//
//                        // Update TextView dengan data dari API
//                        binding.nilaiIncomee.text = "Rp ${it.income}"
//                        binding.nilaiExpensee.text = "Rp ${it.expense}"
//                        binding.nilaiBalancee.text = "Rp ${it.balance}"
//                    }
//                } else {
//                    Log.e("API_ERROR", "Response Code: ${response.code()}, Message: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<SummaryResponse>, t: Throwable) {
//                Log.e("API_FAILURE", "Error: ${t.message}")
//                binding.nilaiIncomee.text = "Error"
//                binding.nilaiExpensee.text = "Error"
//                binding.nilaiBalancee.text = "Error"
//            }
//        })
//    }


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
