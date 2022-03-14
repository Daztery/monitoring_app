package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.PriorityType
import com.example.monitoringapp.databinding.ItemPatientByPriorityBinding
import com.example.monitoringapp.databinding.ItemPatientStatusBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PatientsByPriorityAdapter(
    private var items: List<PriorityType>
) :
    RecyclerView.Adapter<PatientsByPriorityAdapter.CardViewHolder>() {

    inner class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPatientByPriorityBinding.bind(itemView)


        fun bindTo(item: PriorityType) {
            binding.run {
                textName.text = item.patient?.getFullName()
                val date= Formatter.getLocaleDate(item.startDate ?: "")
                textDate.text = Formatter.formatLocalDate(date ?: Date())
                textPriority.text = item.priority?.name
            }

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_patient_by_priority, parent, false)
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
