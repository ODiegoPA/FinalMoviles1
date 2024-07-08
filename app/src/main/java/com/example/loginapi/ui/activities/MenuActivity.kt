package com.example.loginapi.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.view.isVisible
import com.example.loginapi.databinding.ActivityMenuBinding
import com.example.loginapi.models.dto.FoodItem
import com.example.loginapi.models.dto.MenuCategory
import com.example.loginapi.repositories.RestaurantRepository
import com.example.loginapi.adapters.MenuAdapter

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private var restaurantId: Int = 0
    private var selectFood: Boolean = false
    private val selectedFoodItems = mutableListOf<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantId = intent.getIntExtra("restaurantId", 0)
        selectFood = intent.getBooleanExtra("selectFood", false)

        if (restaurantId != 0) {
            fetchMenu(restaurantId)
        }

        setupEventListeners()
    }

    private fun setupEventListeners() {
        binding.rvMenu.layoutManager = LinearLayoutManager(this)

        if (selectFood) {
            binding.btnConfirmFoodSelection.setOnClickListener {
                val intent = Intent().apply {
                    putParcelableArrayListExtra("selectedFood", ArrayList(selectedFoodItems))
                    Log.d("MenuActivity", "Selected food items: $selectedFoodItems")
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        } else {
            Log.d("MenuActivity", "Not selecting food")
            binding.btnConfirmFoodSelection.isVisible = false
        }
    }

    private fun fetchMenu(restaurantId: Int) {
        RestaurantRepository.getMenuById(restaurantId,
            success = { menuCategories ->
                bindMenu(menuCategories)
            },
            failure = { error ->
                Toast.makeText(this, "Error al obtener el menú: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun bindMenu(menuCategories: List<MenuCategory>) {
        val menuAdapter = MenuAdapter(menuCategories, selectFood) { plate, isSelected ->
            if (isSelected) {
                selectedFoodItems.add(FoodItem(plate.id, "1"))
            } else {
                selectedFoodItems.removeAll { it.plate_id == plate.id }
            }
        }
        binding.rvMenu.adapter = menuAdapter
    }
}
