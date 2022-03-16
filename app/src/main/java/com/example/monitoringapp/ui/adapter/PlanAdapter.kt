package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Plan
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.databinding.ItemMedicalHistoryBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PlanAdapter(
    private var items: List<Plan>
) :
    RecyclerView.Adapter<PlanAdapter.CardViewHolder>() {

    class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMedicalHistoryBinding.bind(itemView)


        fun bindTo(item: Plan) {
            binding.run {
                textAttentionNumber.text = item.code.toString()
                textEmergencyType.text = item.emergencyType?.name

                val endDate = Formatter.getLocaleDate(item.endDate ?: "")
                val currentDate = Date()
                textDate.text = Formatter.formatLocalDate(endDate!!)
                if (currentDate < endDate) {
                    textStatus.text = "En monitoreo"
                } else {
                    textStatus.text = "Alta Médica"
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
                .inflate(R.layout.item_medical_history, parent, false)
        return CardViewHolder(inflater)
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
