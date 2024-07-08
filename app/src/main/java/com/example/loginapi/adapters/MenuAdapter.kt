package com.example.loginapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ItemMenuCategoryBinding
import com.example.loginapi.databinding.ItemPlateBinding
import com.example.loginapi.models.dto.MenuCategory
import com.example.loginapi.models.dto.Plate

class MenuAdapter(
    private val menuCategories: List<MenuCategory>,
    private val selectFood: Boolean = false,
    private val onFoodSelected: (Plate, Boolean) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<MenuAdapter.MenuCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryViewHolder {
        val binding = ItemMenuCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuCategoryViewHolder(binding, selectFood, onFoodSelected)
    }

    override fun onBindViewHolder(holder: MenuCategoryViewHolder, position: Int) {
        val category = menuCategories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return menuCategories.size
    }

    class MenuCategoryViewHolder(
        private val binding: ItemMenuCategoryBinding,
        private val selectFood: Boolean,
        private val onFoodSelected: (Plate, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: MenuCategory) {
            binding.lblCategoryName.text = category.name
            binding.rvPlates.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvPlates.adapter = PlateAdapter(category.plates, selectFood, onFoodSelected)
        }
    }

    class PlateAdapter(
        private val plates: List<Plate>,
        private val selectFood: Boolean,
        private val onFoodSelected: (Plate, Boolean) -> Unit
    ) : RecyclerView.Adapter<PlateAdapter.PlateViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlateViewHolder {
            val binding = ItemPlateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PlateViewHolder(binding, selectFood, onFoodSelected)
        }

        override fun onBindViewHolder(holder: PlateViewHolder, position: Int) {
            val plate = plates[position]
            holder.bind(plate)
        }

        override fun getItemCount(): Int {
            return plates.size
        }

        class PlateViewHolder(
            private val binding: ItemPlateBinding,
            private val selectFood: Boolean,
            private val onFoodSelected: (Plate, Boolean) -> Unit
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(plate: Plate) {
                binding.lblPlateName.text = plate.name
                binding.lblPlateDescription.text = plate.description
                binding.lblPlatePrice.text = plate.price

                if (selectFood) {
                    binding.root.setOnClickListener {
                        binding.root.isSelected = !binding.root.isSelected
                        onFoodSelected(plate, binding.root.isSelected)
                    }
                }
            }
        }
    }
}
