package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kelompoksigma.hepengku_.adapter.CategoryAdapter
import com.kelompoksigma.hepengku_.databinding.FragmentAddExpensesBinding
import com.kelompoksigma.hepengku_.retrovit.Category
import com.kelompoksigma.hepengku_.retrovit.RetrofitInstance
import kotlinx.coroutines.launch

class AddExpensesFragment : Fragment() {

    private var _binding: FragmentAddExpensesBinding? = null
    private val binding get() = _binding!!
    private var currentCategoryType: String = "expense" // Default type

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
                    // Handle UI jika kategori kosong
                    return@launch
                }

                setupRecyclerView(filteredCategories)
            } catch (e: Exception) {
                Log.e("AddExpensesFragment", "Error updating categories: ${e.message}")
                // Handle UI untuk error
            }
        }
    }

    private fun setupRecyclerView(categories: List<Category>) {
        val adapter = CategoryAdapter(categories) { category ->
            // Handle klik kategori
            Log.d("AddExpensesFragment", "Selected Category: $category")
        }

        binding.recyclerViewCategories.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.recyclerViewCategories.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}