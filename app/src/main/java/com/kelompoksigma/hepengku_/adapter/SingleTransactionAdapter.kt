package com.kelompoksigma.hepengku_.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksigma.hepengku_.R
import com.kelompoksigma.hepengku_.databinding.ItemTransactionBinding
import com.kelompoksigma.hepengku_.retrovit.Transaction
import com.kelompoksigma.hepengku_.HomeFragmentDirections

// Adapter untuk daftar transaksi dalam satu tanggal
class SingleTransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<SingleTransactionAdapter.TransactionViewHolder>() {

    // ViewHolder untuk item transaksi
    class TransactionViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]

        // Tampilkan nama kategori dan jumlah transaksi
        holder.binding.tvDescription.text = transaction.category_name
        holder.binding.tvAmount.text = "Rp ${transaction.amount}"

        // Memuat ikon berdasarkan nama dari database
        val context = holder.itemView.context
        val iconNameWithoutExtension = transaction.icon.substringBefore(".") // Menghapus .png
        val iconResId = context.resources.getIdentifier(
            iconNameWithoutExtension, // Nama ikon tanpa ekstensi
            "drawable",               // Folder drawable
            context.packageName       // Nama package aplikasi
        )

        // Jika ikon ditemukan, set ke ImageView
        if (iconResId != 0) {
            holder.binding.ivIcon.setImageResource(iconResId)
        } else {
            // Jika ikon tidak ditemukan, gunakan ikon default
            holder.binding.ivIcon.setImageResource(R.drawable.ic_default_icon)
        }

        // Navigasi ke DetailFragment saat item transaksi ditekan
        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                id = transaction.id, // Kirim ID transaksi
                icon = transaction.icon,
                categoryName = transaction.category_name,
                amount = transaction.amount,
                date = transaction.date,
                type = transaction.type,
                note = transaction.note ?: "No Note"

            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = transactions.size
}
