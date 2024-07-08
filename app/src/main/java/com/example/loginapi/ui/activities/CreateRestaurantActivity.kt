package com.example.loginapi.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.loginapi.databinding.ActivityCreateRestaurantBinding
import com.example.loginapi.models.dto.RestaurantRequest
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.CreateRestaurantViewModel


class CreateRestaurantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRestaurantBinding
    private var restaurantId: Int? = null
    private var isEditMode: Boolean = false
    private val createRestaurantViewModel: CreateRestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupEventListeners()
    }

    private fun setupObservers() {
        createRestaurantViewModel.restaurant.observe(this, Observer { restaurant ->
            restaurant?.let {
                binding.txtRestaurantName.setText(it.name)
                binding.txtRestaurantAddress.setText(it.address)
                binding.txtRestaurantCity.setText(it.city)
                binding.txtRestaurantDescription.setText(it.description)
            }
        })

        createRestaurantViewModel.success.observe(this, Observer { success ->
            if (success) {
                val message = if (isEditMode) "Restaurante actualizado con éxito" else "Restaurante creado con éxito"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        createRestaurantViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        restaurantId = intent.getIntExtra("restaurantId", -1)
        isEditMode = restaurantId != -1

        if (isEditMode) {
            createRestaurantViewModel.loadRestaurantDetails(restaurantId!!)
            binding.btnDeleteRestaurant.visibility = View.VISIBLE
            binding.btnCreateRestaurant.text = "Actualizar Restaurante"
        } else {
            binding.btnDeleteRestaurant.visibility = View.GONE
            binding.btnCreateRestaurant.text = "Crear Restaurante"
        }

        binding.btnCreateRestaurant.setOnClickListener {
            if (isEditMode) {
                updateRestaurant()
            } else {
                createRestaurant()
            }
        }

        binding.btnDeleteRestaurant.setOnClickListener {
            deleteRestaurant()
        }
    }

    private fun createRestaurant() {
        val name = binding.txtRestaurantName.text.toString()
        val address = binding.txtRestaurantAddress.text.toString()
        val city = binding.txtRestaurantCity.text.toString()
        val description = binding.txtRestaurantDescription.text.toString()

        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val restaurantRequest = RestaurantRequest(
            name = name,
            address = address,
            city = city,
            description = description
        )

        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            createRestaurantViewModel.createRestaurant(token, restaurantRequest)
        }
    }

    private fun updateRestaurant() {
        val name = binding.txtRestaurantName.text.toString()
        val address = binding.txtRestaurantAddress.text.toString()
        val city = binding.txtRestaurantCity.text.toString()
        val description = binding.txtRestaurantDescription.text.toString()

        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val restaurantRequest = RestaurantRequest(
            name = name,
            address = address,
            city = city,
            description = description
        )

        val token = PreferencesRepository.getToken(this)
        if (token != null && restaurantId != null) {
            createRestaurantViewModel.updateRestaurant(token, restaurantId!!, restaurantRequest)
        }
    }

    private fun deleteRestaurant() {
        val token = PreferencesRepository.getToken(this)
        if (token != null && restaurantId != null) {
            createRestaurantViewModel.deleteRestaurant(token, restaurantId!!)
            val intent = Intent(this, MyRestaurantsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
