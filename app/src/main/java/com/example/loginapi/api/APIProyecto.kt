package com.example.loginapi.api

import com.example.loginapi.models.dto.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIProyecto {
    @POST("loginuser")
    fun login(@Body loginRequest: LoginRequestDTO): Call<LoginResponseDTO>

    @POST("registeruser")
    fun registerUser(@Body registerRequest: RegisterRequestDTO): Call<Void>


    @POST("restaurants/search")
    fun searchRestaurants(): Call<List<Restaurant>>

    @POST("restaurants/search")
    fun searchRestaurantsFiltered(@Body filters: RestaurantFilterRequest): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurantById(@Path("id") restaurantId: Int): Call<Restaurant>

    @GET("restaurants/{id}/menu")
    fun getMenu(@Path("id") restaurantId: Int): Call<List<MenuCategory>>

    @POST("reservations")
    fun createReservation(
        @Header("Authorization") token: String,
        @Body reservationRequest: ReservationRequest
    ): Call<Void>

    @GET("reservations")
    fun getReservations(@Header("Authorization") token: String): Call<List<Reservation>>

    @GET("reservations/{id}")
    fun getReservationById(@Header("Authorization") token: String, @Path("id") reservationId: Int): Call<ReservationDetail>

    @POST("reservations/{id}/cancel")
    fun cancelReservation(@Header("Authorization") token: String, @Path("id") reservationId: Int): Call<Void>

    @GET("restaurants")
    fun getMyRestaurants(@Header("Authorization") token: String): Call<List<Restaurant>>

    @GET("restaurants/{id}")
    fun getRestaurantById(@Header("Authorization") token: String, @Path("id") id: Int): Call<Restaurant>

    @GET("restaurants/{id}/reservations")
    fun getReservationsByRestaurant(@Header("Authorization") token: String, @Path("id") restaurantId: Int): Call<List<Reservation>>

    @POST("restaurants")
    fun insertRestaurant(@Header("Authorization") token: String, @Body restaurantRequest: RestaurantRequest): Call<Restaurant>

    @Multipart
    @POST("restaurants/{id}/logo")
    fun uploadRestaurantLogo(@Header("Authorization") token: String, @Path("id") restaurantId: Int, @Part logo: MultipartBody.Part): Call<Void>

    @Multipart
    @POST("restaurants/{id}/photo")
    fun uploadRestaurantPhoto(@Header("Authorization") token: String, @Path("id") restaurantId: Int, @Part photo: MultipartBody.Part): Call<Void>

    @PUT("restaurants/{id}")
    fun updateRestaurant(@Header("Authorization") token: String, @Path("id") restaurantId: Int, @Body restaurantRequest: RestaurantRequest): Call<Restaurant>

    @DELETE("restaurants/{id}")
    fun deleteRestaurant(@Header("Authorization") token: String, @Path("id") restaurantId: Int): Call<Void>



}
