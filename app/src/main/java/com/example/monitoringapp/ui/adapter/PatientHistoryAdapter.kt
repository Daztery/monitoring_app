package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.databinding.ItemMedicalConsultationBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PatientHistoryAdapter(
    private var items: List<Plan>,
    private val onClickCallback: (plan: Plan) -> Unit
) :
    RecyclerView.Adapter<PatientHistoryAdapter.CardViewHolder>() {

    inner class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMedicalConsultationBinding.bind(itemView)

        fun bindTo(item: Plan) {
            binding.run {
                textPatientName.text = item.patient?.getFullName()
                textCode.text = item.code.toString()
                val startDate = Formatter.getLocaleDate(item.startDate!!)
                val endDate = Formatter.getLocaleDate(item.endDate!!)
                textStartDate.text = Formatter.formatLocalDate(startDate ?: Date())
                textEndDate.text = Formatter.formatLocalDate(endDate ?: Date())
                card.setOnClickListener {
                    onClickCallback(item)
                }
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_medical_consultation, parent, false)
        return CardViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val parent = items[position]
        holder.bindTo(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
