package com.kelompoksigma.hepengku_

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChartIncome : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var navExpenseee: TextView // Tambahkan TextView untuk EXPENSE
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment_chart.xml dan kembalikan sebagai View
        val view = inflater.inflate(R.layout.fragment_chart_income, container, false)

        // Inisialisasi TextView EXPENSE
        navExpenseee = view.findViewById(R.id.navExpenseee)
        navExpenseee.setOnClickListener {
            // Navigasi menggunakan Navigation Component
            findNavController().navigate(R.id.chartFragment)
        }

        // Inisialisasi PieChart dari layout
        pieChart = view.findViewById(R.id.pie_chart)

        // Buat data PieChart
        val list: ArrayList<PieEntry> = ArrayList()
        list.add(PieEntry(40f, "Makanan"))
        list.add(PieEntry(30f, "Transportasi"))
        list.add(PieEntry(20f, "Hiburan"))
        list.add(PieEntry(10f, "Kesehatan"))

        // Buat DataSet untuk PieChart
        val pieDataSet = PieDataSet(list, "Kategori Pengeluaran")
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueTextSize = 16f

        // Buat Data PieChart
        val pieData = PieData(pieDataSet)

        // Atur properti dari PieChart
        pieChart.data = pieData
        pieChart.description.text = "Pengeluaran Bulanan"
        pieChart.centerText = "Kategori"
        pieChart.setUsePercentValues(true) // Menampilkan nilai dalam persen
        pieChart.setEntryLabelColor(Color.BLACK) // Warna label di dalam pie chart
        pieChart.animateY(2000) // Animasi selama 2 detik

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChartIncome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
