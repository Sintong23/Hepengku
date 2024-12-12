package com.kelompoksigma.hepengku_

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kelompoksigma.hepengku_.databinding.FragmentDetailMonthlyBudgetBinding

class DetailMonthly : Fragment() {
    private var _binding: FragmentDetailMonthlyBudgetBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMonthlyBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi kembali ke fragment sebelumnya
        binding.btnBack3.setOnClickListener {
            findNavController().navigateUp()
        }

        // Inisialisasi PieChart dari binding
        pieChart = binding.pieChart

        // Data kosong untuk PieChart
        val list: ArrayList<PieEntry> = ArrayList()
        list.add(PieEntry(1f)) // Placeholder data agar PieChart tetap terlihat

        // Buat DataSet untuk PieChart
        val pieDataSet = PieDataSet(list, null)
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        pieDataSet.setDrawValues(false)

        // Buat Data PieChart
        val pieData = PieData(pieDataSet)

        // Atur properti PieChart
        pieChart.data = pieData
        pieChart.setDrawEntryLabels(false)
        pieChart.setDrawCenterText(false)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setHoleColor(Color.WHITE)
        pieChart.setUsePercentValues(false)
        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleRadius(50f)
        pieChart.setTransparentCircleRadius(55f)
        pieChart.animateY(1000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            DetailMonthly().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}