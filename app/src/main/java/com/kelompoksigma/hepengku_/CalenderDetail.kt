package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentCalenderDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

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

        // Listener ketika tanggal dipilih
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Format tanggal ke bentuk "yyyy-MM-dd"
            val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                java.util.Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }.time
            )
            Log.d("CalenderDetail", "Selected Date: $selectedDate")

            // Navigasi ke DetailCalenderFragment dengan argumen tanggal
            val action =
                CalenderDetailDirections.actionCalenderDetailFragmentToDetailCalenderFragment(
                    selectedDate
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
