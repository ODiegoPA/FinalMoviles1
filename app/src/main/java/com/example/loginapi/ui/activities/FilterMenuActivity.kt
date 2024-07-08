package com.example.loginapi.ui.activities

import FilterMenuViewModel
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.loginapi.databinding.ActivityFilterMenuBinding

class FilterMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterMenuBinding
    private val filterViewModel: FilterMenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterViewModel.filter.observe(this, Observer { filter ->
            filter?.let {
                val resultIntent = Intent().apply {
                    putExtra("city", it.city)
                    putExtra("selectedDate", it.date)
                    putExtra("selectedTime", it.time)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })

        binding.btnApplyFilters.setOnClickListener {
            val city = binding.etCity.text.toString()
            val selectedDate = binding.etDate.text.toString()
            val selectedTime = binding.etTime.text.toString()

            filterViewModel.applyFilters(city, selectedDate, selectedTime)
        }
    }
}
