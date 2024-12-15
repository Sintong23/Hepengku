package com.kelompoksigma.hepengku_

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kelompoksigma.hepengku_.databinding.FragmentAddExpensesBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddExpensesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAddExpensesBinding? = null
    private val binding get() = _binding!!
    private var customKeyboard: View? = null

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
        _binding = FragmentAddExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tambahkan tombol untuk navigasi ke addIncome
        binding.view15.setOnClickListener {
            findNavController().navigate(R.id.addIncomeFragment) // Pastikan ID ini ada di main_nav.xml
        }

        // Tombol untuk navigasi ke home sebelumnya
        binding.tvCancel.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        // Menampilkan keyboard saat tombol Edit ditekan
        binding.ivbeauty.setOnClickListener {
            showCustomKeyboard()
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddExpensesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddExpensesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}