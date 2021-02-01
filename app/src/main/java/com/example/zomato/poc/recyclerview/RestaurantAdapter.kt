package com.example.zomato.poc.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zomato.poc.R
import com.example.zomato.poc.model.restaurant.Restaurant
import kotlinx.android.synthetic.main.recycler_restaurant.view.*

 class RestaurantAdapter : RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    var searchRes = ArrayList<Restaurant>()
    var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun updateData(searchResInfo: ArrayList<Restaurant>) {
        searchRes.clear()
        this.searchRes = searchResInfo
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return searchRes.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.recycler_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, position, searchRes.get(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemView: View = view

        fun bind(context: Context, position: Int, searchRes: Restaurant) {
            itemView.recycler_restaurant_name.text = searchRes.restaurant.name
        }

    }


}