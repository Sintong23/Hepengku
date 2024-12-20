//package com.kelompoksigma.hepengku_
//
//import android.util.Log
//import android.view.View
//import android.widget.TextView
//import android.widget.FrameLayout
//import androidx.lifecycle.lifecycleScope
//import com.kelompoksigma.hepengku_.R
//import com.kelompoksigma.hepengku_.databinding.FragmentAddExpensesBinding
//import kotlinx.coroutines.launch
//
//class KeyboardAdd(private val rootView: FrameLayout, private val binding: FragmentAddExpensesBinding) {
//
//    var onSubmitListener: ((String, String) -> Unit)? = null
//
//    private val inputText: TextView = rootView.findViewById(R.id.input_text)
//
//    init {
//        setupButtonListeners()
//    }
//
//    private fun setupButtonListeners() {
//        rootView.findViewById<View>(R.id.btn_simpan).setOnClickListener {
//            val amount = inputText.text.toString()
//            val categoryName = binding.tvSelectedCategory.text.toString()
//            Log.d("KeyboardAdd", "Tombol simpan ditekan dengan input: $amount dan kategori: $categoryName")
//            onSubmitListener?.invoke(amount, categoryName) // Kirim data ke listener
//            hideKeyboard()
//        }
//
//        rootView.findViewById<View>(R.id.btn_hapus).setOnClickListener { clearText() }
//        rootView.findViewById<View>(R.id.btn_hapus_1_angka).setOnClickListener { deleteLastChar() }
//
//        // Tambahkan logika tombol angka
//        val buttons = listOf(
//            R.id.btn_0 to "0",
//            R.id.btn_1 to "1",
//            R.id.btn_2 to "2",
//            R.id.btn_3 to "3",
//            R.id.btn_4 to "4",
//            R.id.btn_5 to "5",
//            R.id.btn_6 to "6",
//            R.id.btn_7 to "7",
//            R.id.btn_8 to "8",
//            R.id.btn_9 to "9",
//            R.id.btn_titik to "."
//        )
//        for ((id, value) in buttons) {
//            rootView.findViewById<View>(id).setOnClickListener { appendText(value) }
//        }
//    }
//
//    fun showKeyboard(category: String) {
//        rootView.visibility = View.VISIBLE
//        binding.tvSelectedCategory.text = category
//    }
//
//    fun hideKeyboard() {
//        rootView.visibility = View.GONE
//        clearText()
//    }
//
//    private fun appendText(text: String) {
//        val currentText = inputText.text.toString()
//        inputText.text = currentText + text
//    }
//
//    private fun clearText() {
//        inputText.text = ""
//    }
//
//    private fun deleteLastChar() {
//        val currentText = inputText.text.toString()
//        if (currentText.isNotEmpty()) {
//            inputText.text = currentText.substring(0, currentText.length - 1)
//        }
//    }
//}