package com.kelompoksigma.hepengku_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kelompoksigma.hepengku_.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // SafeArgs untuk menerima data
    private val args: DetailFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigasi kembali ke HomeFragment
        binding.imageView3.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        // Tampilkan data yang diterima melalui SafeArgs
        displayReceivedData()

        // Tampilkan custom keyboard
        binding.tvEdit.setOnClickListener {
            showCustomKeyboard()
        }

        // Sembunyikan custom keyboard
        binding.tvDelete.setOnClickListener {
            hideCustomKeyboard()
        }

        // Sembunyikan keyboard saat area di luar keyboard ditekan
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                hideCustomKeyboardIfOutside(event)
            }
            false
        }
    }

    private fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Format input dari API atau database
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()) // Format yang diinginkan
        return try {
            val parsedDate = inputFormat.parse(date) // Parsing tanggal input
            outputFormat.format(parsedDate ?: date) // Format ulang
        } catch (e: Exception) {
            date // Jika parsing gagal, tampilkan tanggal asli
        }
    }

    /**
     * Menampilkan data yang diterima dari SafeArgs
     */
    private fun displayReceivedData() {
        // Set ikon dari nama yang diterima
        val iconResId = requireContext().resources.getIdentifier(
            args.icon.substringBefore("."),
            "drawable",
            requireContext().packageName
        )
        if (iconResId != 0) {
            binding.ivSalary2.setImageResource(iconResId)
        } else {
            binding.ivSalary2.setImageResource(R.drawable.ic_default_icon)
        }

        // Set nama kategori
        binding.tvSalary2.text = args.categoryName ?: "Kategori tidak tersedia"

        // Set jumlah
        binding.tvNilaiMount.text = "Rp ${args.amount ?: 0}"

        // Set tanggal
        binding.tvNilaiDate.text = formatDate(args.date ?: "Tanggal tidak tersedia")

        // Set type
        binding.tvIncome.text = args.type ?: "Tipe tidak tersedia"

        // Set note
        binding.tvNilaiNote.text = args.note ?: "Tidak ada catatan"


    }

    /**
     * Menampilkan custom keyboard
     */
    private fun showCustomKeyboard() {
        if (binding.customKeyboard.visibility == View.GONE) {
            binding.customKeyboard.visibility = View.VISIBLE
        }
    }

    /**
     * Menyembunyikan custom keyboard
     */
    private fun hideCustomKeyboard() {
        if (binding.customKeyboard.visibility == View.VISIBLE) {
            binding.customKeyboard.visibility = View.GONE
        }
    }

    /**
     * Menyembunyikan keyboard jika area di luar keyboard ditekan
     */
    private fun hideCustomKeyboardIfOutside(event: MotionEvent) {
        val keyboardContainer = binding.customKeyboard
        if (keyboardContainer.visibility == View.VISIBLE) {
            val rect = IntArray(2)
            keyboardContainer.getLocationOnScreen(rect)
            val x = event.rawX
            val y = event.rawY

            if (!(x >= rect[0] && x <= rect[0] + keyboardContainer.width &&
                        y >= rect[1] && y <= rect[1] + keyboardContainer.height)) {
                hideCustomKeyboard()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
