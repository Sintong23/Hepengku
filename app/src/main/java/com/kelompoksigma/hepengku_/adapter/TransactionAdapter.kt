package com.kelompoksigma.hepengku_.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksigma.hepengku_.databinding.ItemDateBinding
import com.kelompoksigma.hepengku_.retrovit.Transaction
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(private val groupedTransactions: Map<String, List<Transaction>>) :
    RecyclerView.Adapter<TransactionAdapter.DateViewHolder>() {

    class DateViewHolder(val binding: ItemDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = groupedTransactions.keys.elementAt(position)
        val transactions = groupedTransactions[date]!!

        // Format tanggal
        holder.binding.tvDate.text = formatDate(date)

        // Set adapter untuk nested RecyclerView
        val adapter = SingleTransactionAdapter(transactions)
        holder.binding.recyclerTransactions.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.binding.recyclerTransactions.adapter = adapter
    }

    override fun getItemCount(): Int = groupedTransactions.size

    /**
     * Fungsi untuk memformat tanggal
     * Dari format "YYYY-MM-DD" menjadi "DD MMM YYYY"
     */
    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString // Jika ada error, gunakan tanggal asli
        }
    }



}