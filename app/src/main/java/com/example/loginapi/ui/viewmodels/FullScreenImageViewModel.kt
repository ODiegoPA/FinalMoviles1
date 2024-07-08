package com.example.loginapi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FullScreenImageViewModel : ViewModel() {
    private val _imageUrl = MutableLiveData<String?>()
    val imageUrl: LiveData<String?> get() = _imageUrl

    fun setImageUrl(url: String?) {
        _imageUrl.value = url
    }
}
