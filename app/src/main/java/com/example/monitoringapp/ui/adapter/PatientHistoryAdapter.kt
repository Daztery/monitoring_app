package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.EmergencyType
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.databinding.ItemMedicalConsultationBinding
import com.example.monitoringapp.databinding.ItemPatientByEmergencyBinding
import com.example.monitoringapp.databinding.ItemPatientStatusBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PatientHistoryAdapter(
    private var items: List<Plan>
) :
    RecyclerView.Adapter<PatientHistoryAdapter.CardViewHolder>() {

    inner class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMedicalConsultationBinding.bind(itemView)

        fun bindTo(item: Plan) {
            binding.run {
                textNumberAttention.text = item.code.toString()
                textEmergencyType.text = item.emergencyType?.name
                textStatus.text = item.patient?.status
                textMonitoringTime.text = "1 semana"
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medical_consultation, parent, false)
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
