package com.example.monitoringapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoringapp.R
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.databinding.ItemPatientHistoryBinding

class SearchPatientAdapter(
    private var items: List<User>,
    private val onClickCallback: (user: User) -> Unit,
) :
    RecyclerView.Adapter<SearchPatientAdapter.CardViewHolder>() {

    inner class CardViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPatientHistoryBinding.bind(itemView)

        fun bindTo(item: User) {
            binding.run {
                textName.text = item.patient?.getFullName()
                textDni.text = item.identification
                textDate.text = item.patient?.getBirthday()
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
                .inflate(R.layout.item_patient_history, parent, false)
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
