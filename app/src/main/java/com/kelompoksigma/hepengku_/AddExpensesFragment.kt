package com.kelompoksigma.hepengku_


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.kelompoksigma.hepengku_.R
import com.kelompoksigma.hepengku_.adapter.CategoryAdapter
import com.kelompoksigma.hepengku_.databinding.FragmentAddExpensesBinding
import com.kelompoksigma.hepengku_.retrovit.Category
import com.kelompoksigma.hepengku_.retrovit.RetrofitInstance
import kotlinx.coroutines.launch

class AddExpensesFragment : Fragment() {

    private var _binding: FragmentAddExpensesBinding? = null
    private val binding get() = _binding!!
    private var currentCategoryType: String = "expense" // Default type
    private lateinit var selectedCategory: Category

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listener for expense button
        binding.btnExpense.setOnClickListener {
            currentCategoryType = "expense"
            updateCategoryList()
        }

        // Set click listener for income button
        binding.btnIncome.setOnClickListener {
            currentCategoryType = "income"
            updateCategoryList()
        }

        // Load initial category list
        updateCategoryList()
    }

    private fun updateCategoryList() {
        lifecycleScope.launch {
            try {
                val categories = RetrofitInstance.api.getCategories()
                val filteredCategories = categories.filter { it.type == currentCategoryType }

                if (filteredCategories.isNullOrEmpty()) {
                    Log.e("AddExpensesFragment", "No categories found for $currentCategoryType")
                    return@launch
                }

                setupRecyclerView(filteredCategories)
            } catch (e: Exception) {
                Log.e("AddExpensesFragment", "Error updating categories: ${e.message}")
            }
        }
    }

    private fun setupRecyclerView(categories: List<Category>) {
        val adapter = CategoryAdapter(categories) { category ->
            selectedCategory = category
            showKeyboard() // Menampilkan keyboard
        }

        binding.recyclerViewCategories.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerViewCategories.adapter = adapter
    }

    private fun showKeyboard() {
        val keyboardFragment = layoutInflater.inflate(R.layout.fragment_add, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(keyboardFragment)
            .create()

        val tvNilaiAngka = keyboardFragment.findViewById<TextView>(R.id.tv_nilai_angka)
        val inputNote = keyboardFragment.findViewById<TextInputEditText>(R.id.textInputEditText)
        val btnSimpan = keyboardFragment.findViewById<View>(R.id.btn_simpan)

        var amount = ""
        fun appendAmount(value: String) {
            amount += value
            tvNilaiAngka.text = amount
        }

        keyboardFragment.findViewById<View>(R.id.btn_1).setOnClickListener { appendAmount("1") }
        keyboardFragment.findViewById<View>(R.id.btn_2).setOnClickListener { appendAmount("2") }
        keyboardFragment.findViewById<View>(R.id.btn_3).setOnClickListener { appendAmount("3") }
        keyboardFragment.findViewById<View>(R.id.btn_4).setOnClickListener { appendAmount("4") }
        keyboardFragment.findViewById<View>(R.id.btn_5).setOnClickListener { appendAmount("5") }
        keyboardFragment.findViewById<View>(R.id.btn_6).setOnClickListener { appendAmount("6") }
        keyboardFragment.findViewById<View>(R.id.btn_7).setOnClickListener { appendAmount("7") }
        keyboardFragment.findViewById<View>(R.id.btn_8).setOnClickListener { appendAmount("8") }
        keyboardFragment.findViewById<View>(R.id.btn_9).setOnClickListener { appendAmount("9") }
        keyboardFragment.findViewById<View>(R.id.btn_0).setOnClickListener { appendAmount("0") }

        btnSimpan.setOnClickListener {
            val note = inputNote.text.toString()
            sendDataToDatabase(amount.toIntOrNull() ?: 0, note)
            dialog.dismiss()

        }


        dialog.show()
    }

    private fun sendDataToDatabase(amount: Int, note: String) {
        lifecycleScope.launch {
            try {
                // Kirim data ke server
                val response = RetrofitInstance.api.addTransaction(
                    date = "2024-12-21", // Anda bisa menggunakan tanggal dinamis
                    amount = amount,
                    type = currentCategoryType,
                    categoryId = selectedCategory.id, // Gunakan ID kategori
                    note = note
                )
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("AddExpensesFragment", "Response Error: ${response.code()}, ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Gagal menyimpan data: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("AddExpensesFragment", "Error: ${e.message}")
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}