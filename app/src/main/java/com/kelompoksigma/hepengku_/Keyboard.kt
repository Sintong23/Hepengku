import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.FrameLayout
import com.kelompoksigma.hepengku_.R

class CustomKeyboard(private val rootView: FrameLayout) {

    var onTextSubmitListener: ((String) -> Unit)? = null

    private val inputText: TextView = rootView.findViewById(R.id.input_text)

    init {
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        rootView.findViewById<View>(R.id.btn_simpan).setOnClickListener {
            val text = inputText.text.toString()
            Log.d("CustomKeyboard", "Tombol simpan ditekan dengan input: $text")
            onTextSubmitListener?.invoke(text) // Kirim data ke listener
        }

        rootView.findViewById<View>(R.id.btn_hapus).setOnClickListener { clearText() }
        rootView.findViewById<View>(R.id.btn_hapus_1_angka).setOnClickListener { deleteLastChar() }

        // Tambahkan logika tombol angka
        val buttons = listOf(
            R.id.btn_0 to "0",
            R.id.btn_1 to "1",
            R.id.btn_2 to "2",
            R.id.btn_3 to "3",
            R.id.btn_4 to "4",
            R.id.btn_5 to "5",
            R.id.btn_6 to "6",
            R.id.btn_7 to "7",
            R.id.btn_8 to "8",
            R.id.btn_9 to "9",
            R.id.btn_titik to "."
        )
        for ((id, value) in buttons) {
            rootView.findViewById<View>(id).setOnClickListener { appendText(value) }
        }
    }

    private fun appendText(text: String) {
        val currentText = inputText.text.toString()
        inputText.text = currentText + text
    }

    private fun clearText() {
        inputText.text = ""
    }

    private fun deleteLastChar() {
        val currentText = inputText.text.toString()
        if (currentText.isNotEmpty()) {
            inputText.text = currentText.substring(0, currentText.length - 1)
        }
    }
}

