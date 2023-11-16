package com.example.e_commerce.Adapters

import android.content.om.OverlayManagerTransaction.newInstance
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Fragments.ProductFragment
import com.example.e_commerce.R

class CategoriesAdapter(private val categories: List<String>, private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryTextView.text = category
        holder.categoryTextView.setOnClickListener{view ->
            openFragment(category, fragmentManager)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
    private fun openFragment(category: String, fragmentManager: FragmentManager) {
        // Create a new instance of your fragment and pass any necessary data
        val fragment = ProductFragment.newInstance(category)

        // Use the FragmentManager to begin a transaction, replace the current fragment, and add the transaction to the back stack
        fragmentManager.beginTransaction()
            .replace(R.id.HomeContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}