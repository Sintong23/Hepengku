package com.kelompoksigma.hepengku_.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksigma.hepengku_.databinding.ItemDetailTransactionBinding
import com.kelompoksigma.hepengku_.retrovit.Transaction

class DetailTransactionAdapter(
    private val context: Context,
    private val transactions: List<Transaction>
) : RecyclerView.Adapter<DetailTransactionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDetailTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailTransactionBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.binding.tvCategory.text = transaction.category_name
        holder.binding.tvAmount.text = "Rp ${String.format("%,d", transaction.amount)}"
        val iconResId = context.resources.getIdentifier(
            transaction.icon.substringBefore("."),
            "drawable",
            context.packageName
        )
        if (iconResId != 0) {
            holder.binding.ivIcon.setImageResource(iconResId)
        } else {
            holder.binding.ivIcon.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = transactions.size
}