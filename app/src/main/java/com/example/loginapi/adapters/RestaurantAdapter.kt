package com.example.loginapi.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapi.databinding.RestaurantItemBinding
import com.example.loginapi.models.dto.Restaurant

class RestaurantAdapter(
    private val restaurantList: MutableList<Restaurant>,
    private val listener: OnRestaurantClickListener
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = RestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurantList[position]
        holder.bind(restaurant, listener)
    }

    fun updateData(newRestaurantList: List<Restaurant>) {
        restaurantList.clear()
        restaurantList.addAll(newRestaurantList)
        notifyDataSetChanged()
    }

    class RestaurantViewHolder(private val binding: RestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant, listener: OnRestaurantClickListener) {
            binding.apply {
                lblRestaurantName.text = restaurant.name
                lblRestaurantDescription.text = restaurant.description
                lblRestaurantCity.text = restaurant.city
                Glide.with(itemView.context).load(restaurant.logo).into(imgRestaurantLogo)

                // Implementar cualquier acción con el listener
                root.setOnClickListener {
                    listener.onRestaurantClick(restaurant)
                }
            }
        }
    }

    interface OnRestaurantClickListener {
        fun onRestaurantClick(restaurant: Restaurant)
    }
}
