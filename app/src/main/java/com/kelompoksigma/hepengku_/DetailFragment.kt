package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var customKeyboard: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi ke home saat ImageView ditekan
        binding.imageView3.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        // Menampilkan keyboard saat tombol Edit ditekan
        binding.view17.setOnClickListener {
            showCustomKeyboard()
        }

        // Menyembunyikan keyboard saat tombol Delete ditekan
        binding.tvDelete.setOnClickListener {
            hideCustomKeyboard()
        }

        // Menambahkan listener untuk mendeteksi klik di luar keyboard
        binding.root.setOnTouchListener { v, event ->
            val keyboardContainer = binding.root.findViewById<ViewGroup>(R.id.custom_keyboard)
            if (keyboardContainer.visibility == View.VISIBLE) {
                val rect = IntArray(2)
                keyboardContainer.getLocationOnScreen(rect)
                val x = event.rawX
                val y = event.rawY

                // Periksa apakah area yang ditekan berada di luar keyboard
                if (!(x >= rect[0] && x <= rect[0] + keyboardContainer.width &&
                            y >= rect[1] && y <= rect[1] + keyboardContainer.height)) {
                    hideCustomKeyboard()
                }
            }
            false // Jangan cegah event sentuh lainnya
        }
    }


    /**
     * Menampilkan custom keyboard
     */
    private fun showCustomKeyboard() {
        val keyboardContainer = binding.root.findViewById<ViewGroup>(R.id.custom_keyboard)
        if (customKeyboard == null) {
            // Inflate custom keyboard dari layout jika belum ada
            customKeyboard = LayoutInflater.from(requireContext()).inflate(
                R.layout.custom_keyboard,
                keyboardContainer,
                false
            )
        }

        // Tambahkan keyboard ke container jika belum ada
        if (keyboardContainer.childCount == 0) {
            keyboardContainer.addView(customKeyboard)
        }

        keyboardContainer.visibility = View.VISIBLE
    }

    /**
     * Menyembunyikan custom keyboard
     */
    private fun hideCustomKeyboard() {
        val keyboardContainer = binding.root.findViewById<ViewGroup>(R.id.custom_keyboard)
        keyboardContainer.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}
