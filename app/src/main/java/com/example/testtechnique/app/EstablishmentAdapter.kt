package com.example.testtechnique.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.testtechnique.R

class EstablishmentAdapter(private val listener: EstablishmentItemListener) : RecyclerView.Adapter<EstablishmentViewHolder>() {

    interface EstablishmentItemListener {
        fun onClickedEstablishment(establishmentName: String)
    }

    private val items = ArrayList<EstablishmentModel>()
    private lateinit var establishment: EstablishmentModel

    fun setItems(items: ArrayList<EstablishmentModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstablishmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_establishment, parent, false)
        return EstablishmentViewHolder(view, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EstablishmentViewHolder, position: Int) {
        establishment = items[position]
        holder.textName.text = establishment.name
        holder.textCity.text = establishment.city
    }
}

class EstablishmentViewHolder(itemView: View, private val listener: EstablishmentAdapter.EstablishmentItemListener) :
    RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
    val itemLayout: ConstraintLayout = itemView.findViewById(R.id.establishment_layout)
    val textName: TextView = itemView.findViewById(R.id.tv_establishment_name)
    val textCity: TextView = itemView.findViewById(R.id.tv_establishment_city)

    init {
        itemLayout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        listener.onClickedEstablishment(textName.text.toString())
    }
}