package com.example.loginapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapi.databinding.ItemReservationBinding
import com.example.loginapi.models.dto.Reservation

class ReservationAdapter(
    private val reservationList: MutableList<Reservation>,
    private val listener: OnReservationClickListener
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = ItemReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservationList[position]
        holder.bind(reservation, listener)
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    fun updateData(newReservationList: List<Reservation>) {
        reservationList.clear()
        reservationList.addAll(newReservationList)
        notifyDataSetChanged()
    }

    class ReservationViewHolder(private val binding: ItemReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reservation: Reservation, listener: OnReservationClickListener) {
            binding.apply {
                lblRestaurantName.text = reservation.restaurant?.name ?: "Nombre no disponible"
                lblReservationDate.text = reservation.date
                lblReservationTime.text = reservation.time
                lblReservationPeople.text = reservation.people.toString()
                root.setOnClickListener {
                    listener.onReservationClick(reservation)
                }
            }
        }
    }

    interface OnReservationClickListener {
        fun onReservationClick(reservation: Reservation)
    }
}
