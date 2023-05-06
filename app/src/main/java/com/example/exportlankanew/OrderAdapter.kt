package com.example.exportlankanew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private var orderList: ArrayList<order> = ArrayList()
    private var onClickItem: ((order) -> Unit)? = null
    private var onClickDeletedItem: ((order) -> Unit)? = null

    fun addItems(items: ArrayList<order>) {
        this.orderList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (order) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (order) ->Unit){
        this.onClickDeletedItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.single_order, parent, false)
    )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val productOrder = orderList[position]
        holder.bindView(productOrder)
        holder.itemView.setOnClickListener { onClickItem?.invoke(productOrder)}
        holder.btnDelete.setOnClickListener{ onClickDeletedItem?.invoke(productOrder)}
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrderViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var pname = view.findViewById<TextView>(R.id.aoproductnamebox)
        private var weight = view.findViewById<TextView>(R.id.aoweightbox)
        private var quantity = view.findViewById<TextView>(R.id.aoquantitybox)
        private var budget = view.findViewById<TextView>(R.id.aobudgetbox)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(productOrder: order) {
            pname.text = productOrder.productName
            weight.text = productOrder.weight.toString()
            quantity.text = productOrder.quantity.toString()
            budget.text = productOrder.budget.toString()

        }
    }

}