package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tambahkan tombol untuk navigasi ke EditProfile
//        binding.btnEditProfile.setOnClickListener {
//            findNavController().navigate(R.id.editProfileFragment) // Pastikan ID ini ada di main_nav.xml
//        }

        // Tambahkan tombol untuk navigasi ke EditProfile
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.settingFragment) // Pastikan ID ini ada di main_nav.xml
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
