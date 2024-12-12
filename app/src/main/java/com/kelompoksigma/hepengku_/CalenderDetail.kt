package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentCalenderDetailBinding

class CalenderDetail : Fragment() {
    private var _binding: FragmentCalenderDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalenderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi kembali ke HomeFragment saat tombol back ditekan
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        // Referensi ke CalendarView
        val calendarView = binding.calendarView

        // Listener ketika tanggal dipilih
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            // Navigasi ke DetailCalender dengan membawa data tanggal
            val bundle = bundleOf("selectedDate" to selectedDate)
            findNavController().navigate(R.id.DetailCalenderFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
