package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Prescription
import com.example.monitoringapp.databinding.ItemMedicalHistoryBinding
import com.example.monitoringapp.databinding.ItemPrescriptionBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PrescriptionAdapter(
    private var items: List<Prescription>
) :
    RecyclerView.Adapter<PrescriptionAdapter.CardViewHolder>() {

    class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPrescriptionBinding.bind(itemView)


        fun bindTo(item: Prescription) {
            binding.run {
                textName.text = item.code.toString()
                val date= Formatter.getLocaleDate(item.createdAt ?: "")
                textDate.text = Formatter.formatLocalDate(date ?: Date())
                when {
                    item.medicine2.isNullOrEmpty() -> {
                        textMedicine.text = "${item.medicine1}"
                    }
                    item.medicine3.isNullOrEmpty() -> {
                        textMedicine.text = "${item.medicine1}, ${item.medicine2}"
                    }
                    item.medicine4.isNullOrEmpty() -> {
                        textMedicine.text = "${item.medicine1}, ${item.medicine2}, ${item.medicine3}"
                    }
                    item.medicine5.isNullOrEmpty() -> {
                        textMedicine.text = "${item.medicine1}, ${item.medicine2}, ${item.medicine3}, ${item.medicine5}"
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_prescription, parent, false)
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
