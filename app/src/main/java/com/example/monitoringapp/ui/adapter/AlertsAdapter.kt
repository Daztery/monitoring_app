package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.databinding.ItemAlertBinding

class AlertsAdapter(
    private var items: List<Alert>
) :
    RecyclerView.Adapter<AlertsAdapter.CardViewHolder>() {

    class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemAlertBinding.bind(itemView)


        fun bindTo(item: Alert) {
            binding.run {
                textAlertType.text = item.alertType
                textDescription.text = item.description
                textPatient.text = item.namePatient
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alert, parent, false)
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
