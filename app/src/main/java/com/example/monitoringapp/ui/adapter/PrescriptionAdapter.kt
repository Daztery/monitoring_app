package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.databinding.ItemMedicalHistoryBinding

class PrescriptionAdapter(
    private var items: List<Prescription>
) :
    RecyclerView.Adapter<PrescriptionAdapter.CardViewHolder>() {

    private var selectedPos = 0
    private var selectedPrice = 0

    class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemMedicalHistoryBinding.bind(itemView)


        fun bindTo(item: Prescription) {
            binding.run {
                textAttentionNumber.text = item.code.toString()
                //textEmergencyType.text = item.
                //textStatus.text = "En monitoreo"
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_medical_history, parent, false)
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
