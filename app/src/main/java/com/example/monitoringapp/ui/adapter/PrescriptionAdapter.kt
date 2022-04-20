package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ItemMedicalHistoryBinding
import com.example.monitoringapp.databinding.ItemPrescriptionBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PrescriptionAdapter(
    private var items: List<Prescription>,
    private var user: User
) :
    RecyclerView.Adapter<PrescriptionAdapter.CardViewHolder>() {

    class CardViewHolder(
        itemView: View,
        var user: User
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPrescriptionBinding.bind(itemView)


        fun bindTo(item: Prescription) {
            binding.run {
                textName.text = user.patient?.getFullName()
                textCode.text = item.code.toString()
                val date= Formatter.getLocaleDate(item.createdAt ?: "")
                textDate.text = Formatter.formatLocalDate(date ?: Date())
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_prescription, parent, false)
        return CardViewHolder(inflater,user)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int,
    ) {
        val parent = items[position]
        holder.bindTo(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
