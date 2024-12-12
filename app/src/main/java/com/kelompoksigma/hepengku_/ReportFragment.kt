package com.kelompoksigma.hepengku_

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kelompoksigma.hepengku_.databinding.FragmentReportBinding

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi tombol ke EditProfileFragment
        binding.imageView6.setOnClickListener {
            findNavController().navigate(R.id.detailmonthly) // Pastikan ID ini ada di main_nav.xml
        }

        // Inisialisasi PieChart dari layout
        pieChart = binding.pieChart // Pastikan ID pieChart di layout fragment_report.xml sesuai

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
