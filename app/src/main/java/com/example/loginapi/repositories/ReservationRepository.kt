package com.example.loginapi.repositories

import com.example.loginapi.api.APIProyecto
import com.example.loginapi.models.dto.Reservation
import com.example.loginapi.models.dto.ReservationDetail
import com.example.loginapi.models.dto.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ReservationRepository {
    fun getReservations(token: String, success: (List<Reservation>) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getReservations("Bearer $token").enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    val reservations = response.body()?.filter { it.status == "pending" } ?: emptyList()
                    success(reservations)
                } else {
                    failure(Exception("Error al obtener reservas"))
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getReservationById(token: String, reservationId: Int, success: (ReservationDetail) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getReservationById("Bearer $token", reservationId).enqueue(object : Callback<ReservationDetail> {
            override fun onResponse(call: Call<ReservationDetail>, response: Response<ReservationDetail>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        success(it)
                    } ?: failure(Exception("No se encontraron detalles de la reserva"))
                } else {
                    failure(Exception("Error al obtener detalles de la reserva"))
                }
            }

            override fun onFailure(call: Call<ReservationDetail>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun cancelReservation(token: String, reservationId: Int, success: () -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.cancelReservation("Bearer $token", reservationId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Error al cancelar la reserva"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getReservationsByRestaurant(token: String, restaurantId: Int, success: (List<Reservation>) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.getReservationsByRestaurant("Bearer $token", restaurantId).enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    val reservations = response.body()?.filter { it.status == "pending" } ?: emptyList()
                    success(reservations)
                    success(reservations)
                } else {
                    failure(Exception("Error al obtener reservas del restaurante"))
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                failure(t)
            }
        })
    }
}
