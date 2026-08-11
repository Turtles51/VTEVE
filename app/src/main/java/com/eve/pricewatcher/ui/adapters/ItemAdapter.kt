package com.eve.pricewatcher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eve.pricewatcher.R
import com.eve.pricewatcher.database.entities.Item

class ItemAdapter(
    private val onEditClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var items = listOf<Item>()

    fun submitList(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOrderTypeIcon: TextView = itemView.findViewById(R.id.tvOrderTypeIcon)
        private val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        private val tvItemId: TextView = itemView.findViewById(R.id.tvItemId)
        private val tvOrderType: TextView = itemView.findViewById(R.id.tvOrderType)
        private val tvThreshold: TextView = itemView.findViewById(R.id.tvThreshold)
        private val tvInterval: TextView = itemView.findViewById(R.id.tvInterval)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvRegion: TextView = itemView.findViewById(R.id.tvRegion)
        private val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(item: Item) {
            // Иконка и тип ордера
            if (item.orderType == "sell") {
                tvOrderTypeIcon.text = "📉"
                tvOrderType.text = "Ордера на продажу"
                tvThreshold.text = "Дешевле: ${item.threshold} ISK"
            } else {
                tvOrderTypeIcon.text = "📈"
                tvOrderType.text = "Ордера на покупку"
                tvThreshold.text = "Дороже: ${item.threshold} ISK"
            }

            tvItemName.text = item.itemName
            tvItemId.text = "ID: ${item.itemId}"
            tvInterval.text = "⏱️ ${item.intervalMinutes} мин"

            // Последняя цена и статус
            if (item.lastPrice != null && item.lastRegion != null) {
                tvPrice.text = "💰 ${item.lastPrice} ISK"
                tvRegion.text = "${item.lastRegion}, ${item.lastUpdate ?: ""}"
                tvStatus.text = if (item.active) "🟢 Ожидание" else "⏸️ Выключен"
                tvStatus.setTextColor(
                    if (item.active) itemView.context.getColor(R.color.success)
                    else itemView.context.getColor(R.color.text_secondary)
                )
            } else {
                tvPrice.text = "⏳ Нет данных"
                tvRegion.text = "Ожидание проверки"
                tvStatus.text = if (item.active) "🟢 Активен" else "⏸️ Выключен"
                tvStatus.setTextColor(
                    if (item.active) itemView.context.getColor(R.color.success)
                    else itemView.context.getColor(R.color.text_secondary)
                )
            }

            btnEdit.setOnClickListener { onEditClick(item) }
            btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }
}
