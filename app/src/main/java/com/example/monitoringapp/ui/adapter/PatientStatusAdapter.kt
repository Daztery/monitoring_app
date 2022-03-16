package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Status
import com.example.monitoringapp.databinding.ItemPatientStatusBinding
import com.example.monitoringapp.util.Formatter
import java.util.*

class PatientStatusAdapter(
    private var items: List<Status>
) :
    RecyclerView.Adapter<PatientStatusAdapter.CardViewHolder>() {

    inner class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPatientStatusBinding.bind(itemView)


        fun bindTo(item: Status) {
            binding.run {
                textName.text = item.getFullName()
                val date= Formatter.getLocaleDate(item.date ?: "")
                textDate.text = Formatter.formatLocalDate(date ?: Date())
                if(item.status=="NOT REPORTED"){
                    textStatus.text = "No reportado"
                }else{
                    textStatus.text = "Reportado"
                }
            }

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_patient_status, parent, false)
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
