package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.Alert
import com.example.monitoringapp.databinding.ItemAlertDoctorBinding

class AlertsDoctorAdapter(
    private var items: List<Alert>
) :
    RecyclerView.Adapter<AlertsDoctorAdapter.CardViewHolder>() {

    class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemAlertDoctorBinding.bind(itemView)


        fun bindTo(item: Alert) {
            binding.run {
                textDoctorName.text = item.dailyReport?.monitoringPlan?.patient?.getFullName()
                textAlertType.text = item.alertType
                textEmergencyType.text = item.dailyReport?.monitoringPlan?.emergencyType?.name
                textPriority.text = item.dailyReport?.monitoringPlan?.priority?.name
                textCellphone.text = item.dailyReport?.monitoringPlan?.patient?.phone
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alert_doctor, parent, false)
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
