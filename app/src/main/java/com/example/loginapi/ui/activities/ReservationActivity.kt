package com.example.loginapi.ui.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.loginapi.databinding.ActivityReservationBinding
import com.example.loginapi.models.dto.FoodItem
import com.example.loginapi.models.dto.ReservationRequest
import com.example.loginapi.repositories.PreferencesRepository
import com.example.loginapi.ui.viewmodels.ReservationViewModel
import java.util.*

class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationBinding
    private var restaurantId: Int = 0
    private val reservationViewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupEventListeners()
    }

    private fun setupObservers() {
        reservationViewModel.date.observe(this, Observer { date ->
            binding.txtDate.setText(date)
        })

        reservationViewModel.time.observe(this, Observer { time ->
            binding.txtTime.setText(time)
        })

        reservationViewModel.selectedFoodItems.observe(this, Observer { foodItems ->

        })

        reservationViewModel.reservationSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Reserva realizada con éxito", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        reservationViewModel.error.observe(this, Observer { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })
    }

    private fun setupEventListeners() {
        restaurantId = intent.getIntExtra("restaurantId", 0)
        binding.btnSelectDate.setOnClickListener {
            showDatePicker()
        }
        binding.btnSelectTime.setOnClickListener {
            showTimePicker()
        }
        binding.btnSelectFood.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("restaurantId", restaurantId)
                putExtra("selectFood", true)
            }
            startActivityForResult(intent, REQUEST_SELECT_FOOD)
        }
        binding.btnConfirmReservation.setOnClickListener {
            confirmReservation()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            reservationViewModel.setDate(selectedDate)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
            val selectedTime = "$hourOfDay:$minute"
            reservationViewModel.setTime(selectedTime)
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    private fun confirmReservation() {
        val date = binding.txtDate.text.toString()
        val time = binding.txtTime.text.toString()
        val people = binding.txtPeople.text.toString().toInt()

        val reservationRequest = ReservationRequest(
            restaurant_id = restaurantId,
            date = date,
            time = time,
            people = people,
            food = reservationViewModel.selectedFoodItems.value ?: emptyList()
        )

        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            reservationViewModel.createReservation(token, reservationRequest)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("message", "Por favor, inicie sesión para realizar una reserva")
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SELECT_FOOD && resultCode == RESULT_OK) {
            val selectedFood = data?.getParcelableArrayListExtra<FoodItem>("selectedFood")
            selectedFood?.let {
                reservationViewModel.setSelectedFoodItems(it)
            }
        }
    }

    companion object {
        const val REQUEST_SELECT_FOOD = 1
    }
}
