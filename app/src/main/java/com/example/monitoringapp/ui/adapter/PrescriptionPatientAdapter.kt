package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ItemPatientPrescriptionBinding
import com.example.monitoringapp.databinding.ItemPrescriptionBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PrescriptionPatientAdapter(
    private var items: List<Prescription>
) :
    RecyclerView.Adapter<PrescriptionPatientAdapter.CardViewHolder>() {

    class CardViewHolder(
        itemView: View
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPatientPrescriptionBinding.bind(itemView)


        fun bindTo(item: Prescription) {
            binding.run {
                textCode.text = item.code.toString()
                textIndications.text = item.instructions
                when {
                    item.medicine2.isNullOrEmpty() -> {
                        textMedicines.text = "${item.medicine1}"
                    }
                    item.medicine3.isNullOrEmpty() -> {
                        textMedicines.text = "${item.medicine1}, ${item.medicine2}"
                    }
                    item.medicine4.isNullOrEmpty() -> {
                        textMedicines.text = "${item.medicine1}, ${item.medicine2}, ${item.medicine3}"
                    }
                    item.medicine5.isNullOrEmpty() -> {
                        textMedicines.text = "${item.medicine1}, ${item.medicine2}, ${item.medicine3}, ${item.medicine5}"
                    }
                }
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_patient_prescription, parent, false)
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
