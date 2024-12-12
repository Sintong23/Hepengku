package com.kelompoksigma.hepengku_

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailMonthly.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailMonthly : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        val view = inflater.inflate(R.layout.fragment_detail_monthly_budget, container, false)

        // Inisialisasi PieChart dari layout
        pieChart = view.findViewById(R.id.pie_chart)

        // Data kosong agar tetap ada tampilan PieChart, meskipun tanpa data
        val list: ArrayList<PieEntry> = ArrayList()
        list.add(PieEntry(1f)) // Tambahkan satu bagian kosong hanya agar pie chart terlihat

        // Buat DataSet untuk PieChart
        val pieDataSet = PieDataSet(list, null) // Tidak ada label
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList() // Warna bisa disesuaikan
        pieDataSet.setDrawValues(false) // Hapus nilai di dalam chart

        // Buat Data PieChart
        val pieData = PieData(pieDataSet)

        // Atur properti dari PieChart
        pieChart.data = pieData
        pieChart.setDrawEntryLabels(false) // Hapus label dari entri
        pieChart.setDrawCenterText(false) // Hapus teks tengah
        pieChart.description.isEnabled = false // Hapus deskripsi
        pieChart.legend.isEnabled = false // Hapus legend (keterangan)
        pieChart.setHoleColor(Color.WHITE) // Warna di tengah lingkaran
        pieChart.setUsePercentValues(false) // Jangan gunakan persentase
        pieChart.setDrawHoleEnabled(true) // Tetap ada lubang di tengah
        pieChart.setHoleRadius(50f) // Ukuran lubang tengah
        pieChart.setTransparentCircleRadius(55f) // Ukuran lingkaran luar (transparan)
        pieChart.animateY(1000) // Animasi selama 1 detik

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailMonthly.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailMonthly().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}