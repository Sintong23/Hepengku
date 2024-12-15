package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tambahkan tombol untuk navigasi ke profile
        binding.btnBack4.setOnClickListener {
            findNavController().navigate(R.id.profileFragment) // Pastikan ID ini ada di main_nav.xml
        }

       // Tambahkan tombol untuk navigasi ke EditProfile
        binding.view43.setOnClickListener {
            findNavController().navigate(R.id.syaratDanKetentuan) // Pastikan ID ini ada di main_nav.xml
        }

        // Tambahkan tombol untuk navigasi ke kebijakan dan privasi
        binding.view44.setOnClickListener {
            findNavController().navigate(R.id.kebijakanPrivasi) // Pastikan ID ini ada di main_nav.xml
        }

        // Tambahkan tombol untuk navigasi ke tentang kami
        binding.view45.setOnClickListener {
            findNavController().navigate(R.id.tentangKami) // Pastikan ID ini ada di main_nav.xml
        }


    }
}