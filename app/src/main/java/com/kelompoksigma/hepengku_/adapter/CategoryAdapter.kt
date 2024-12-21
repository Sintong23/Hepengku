package com.kelompoksigma.hepengku_.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompoksigma.hepengku_.R
import com.kelompoksigma.hepengku_.retrovit.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.tvCategoryName.text = category.name
        holder.ivIcon.setImageResource(
            holder.itemView.context.resources.getIdentifier(
                category.icon.substringBefore("."), "drawable", holder.itemView.context.packageName
            ).takeIf { it != 0 } ?: R.drawable.ic_default_icon
        )
        holder.itemView.setOnClickListener { onClick(category) } // Klik listener
    }


    override fun getItemCount(): Int = categories.size
}