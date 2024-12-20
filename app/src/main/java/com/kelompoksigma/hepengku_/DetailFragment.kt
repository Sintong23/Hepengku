package com.kelompoksigma.hepengku_

import CustomKeyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kelompoksigma.hepengku_.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import android.util.Log
import android.widget.FrameLayout
import com.kelompoksigma.hepengku_.retrovit.RetrofitInstance
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // SafeArgs untuk menerima data
    private val args: DetailFragmentArgs by navArgs()

    private var customKeyboard: CustomKeyboard? = null


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
        binding.btnEdit.setOnClickListener {
            showCustomKeyboard()
        }

        // Sembunyikan custom keyboard
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
        val id = args.id

        Log.d("DetailFragment", "Transaction ID: $id")

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
        Log.d("DetailFragment", "Tombol edit ditekan, menampilkan custom keyboard")

        val keyboardContainer = binding.root.findViewById<FrameLayout>(R.id.custom_keyboard)

        if (keyboardContainer.childCount == 0) {
            // Inflate layout custom_keyboard ke dalam FrameLayout jika belum ada
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_keyboard, keyboardContainer, true)
        }

        if (customKeyboard == null) {
            customKeyboard = CustomKeyboard(keyboardContainer)
            customKeyboard?.onTextSubmitListener = { newAmount ->
                Log.d("DetailFragment", "Input dari tombol simpan: $newAmount")
                val amount = newAmount.toIntOrNull()
                if (amount != null) {
                    updateTransactionAmount(args.id, amount)
                    hideCustomKeyboard()
                } else {
                    Log.e("DetailFragment", "Input tidak valid: $newAmount")
                }
            }
        }

        // Pastikan keyboard terlihat
        keyboardContainer.visibility = View.VISIBLE
    }

    /**
     * Menyembunyikan custom keyboard
     */
    private fun hideCustomKeyboard() {
        val keyboardContainer = binding.root.findViewById<FrameLayout>(R.id.custom_keyboard)
        keyboardContainer.visibility = View.GONE
    }


    private fun updateTransactionAmount(transactionId: Int, newAmount: Int) {
        lifecycleScope.launch {
            try {
                val payload = mapOf("amount" to newAmount)
                val response = RetrofitInstance.api.updateAmount(transactionId, payload)

                if (response.isSuccessful) {
                    val updatedTransaction = response.body()?.transaction
                    Log.d("DetailFragment", "Amount berhasil diperbarui: ${updatedTransaction?.amount}")

                    // Perbarui UI
                    binding.tvNilaiMount.text = "Rp ${updatedTransaction?.amount ?: newAmount}"

                    // Navigasi ke HomeFragment setelah berhasil
                    findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                    Log.d("DetailFragment", "Navigasi ke HomeFragment setelah update berhasil")

                } else {
                    Log.e("DetailFragment", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DetailFragment", "Exception: ${e.message}")
            }
        }
    }






    /**
     * Menyembunyikan keyboard jika area di luar keyboard ditekan
     */
//    private fun hideCustomKeyboardIfOutside(event: MotionEvent) {
//        val keyboardContainer = binding.customKeyboard
//        if (keyboardContainer.visibility == View.VISIBLE) {
//            val rect = IntArray(2)
//            keyboardContainer.getLocationOnScreen(rect)
//            val x = event.rawX
//            val y = event.rawY
//
//            if (!(x >= rect[0] && x <= rect[0] + keyboardContainer.width &&
//                        y >= rect[1] && y <= rect[1] + keyboardContainer.height)) {
//                hideCustomKeyboard()
//            }
//        }
//    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
