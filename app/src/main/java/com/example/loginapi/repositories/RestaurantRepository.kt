package com.example.loginapi.repositories

import android.content.Context
import android.net.Uri
import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

object RestaurantRepository {
    fun searchRestaurants(success: (List<Restaurant>) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.searchRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se encontraron restaurantes"))
                } else {
                    failure(Exception("Error en la búsqueda de restaurantes"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getRestaurantById(restaurantId: Int, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getRestaurantById(restaurantId).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se encontraron detalles del restaurante"))
                } else {
                    failure(Exception("Error al obtener detalles del restaurante"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getMenuById(restaurantId: Int, success: (List<MenuCategory>) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getMenu(restaurantId).enqueue(object : Callback<List<MenuCategory>> {
            override fun onResponse(call: Call<List<MenuCategory>>, response: Response<List<MenuCategory>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se encontraron categorías de menú"))
                } else {
                    failure(Exception("Error al obtener el menú"))
                }
            }

            override fun onFailure(call: Call<List<MenuCategory>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun createReservation(token: String, reservationRequest: ReservationRequest, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.createReservation("Bearer $token", reservationRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Error al crear la reserva"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun searchRestaurantsFiltered(
        city: String?,
        selectedDate: String?,
        selectedTime: String?,
        success: (List<Restaurant>) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        val filterRequest = RestaurantFilterRequest(city, selectedDate, selectedTime)

        println("Filter Request: $filterRequest")

        service.searchRestaurantsFiltered(filterRequest).enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se encontraron restaurantes"))
                } else {
                    failure(Exception("Error en la búsqueda de restaurantes"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }
    fun getMyRestaurants(token: String, success: (List<Restaurant>) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getMyRestaurants("Bearer $token").enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se encontraron restaurantes"))
                } else {
                    failure(Exception("Error al obtener mis restaurantes"))
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                failure(t)
            }
        })
    }
    fun createRestaurant(token: String, restaurantRequest: RestaurantRequest, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.insertRestaurant("Bearer $token", restaurantRequest).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se pudo crear el restaurante"))
                } else {
                    failure(Exception("Error al crear el restaurante"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateRestaurant(token: String, restaurantId: Int, restaurantRequest: RestaurantRequest, success: (Restaurant) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.updateRestaurant("Bearer $token", restaurantId, restaurantRequest).enqueue(object : Callback<Restaurant> {
            override fun onResponse(call: Call<Restaurant>, response: Response<Restaurant>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se pudo actualizar el restaurante"))
                } else {
                    failure(Exception("Error al actualizar el restaurante"))
                }
            }

            override fun onFailure(call: Call<Restaurant>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurant(token: String, restaurantId: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.deleteRestaurant("Bearer $token", restaurantId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Error al eliminar el restaurante"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}
