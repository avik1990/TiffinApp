package com.app.tiffinapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.ConfirmDeliveryAddress
import com.app.tiffinapp.R
import com.app.tiffinapp.models.SubscriptionOrderResponse

class SubscriptionOrderAdapter(
    val userList: ArrayList<SubscriptionOrderResponse>,
    val context: Context,
    val menuId: String, val mealPlanId: String, val menuSlotId: String
) :
    RecyclerView.Adapter<SubscriptionOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubscriptionOrderAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_subscription_order, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: SubscriptionOrderAdapter.ViewHolder, position: Int) {
        holder.tvDescription.text = userList!!.get(position).desc
        holder.tvConisDesc.text = userList!!.get(position).coins
        holder.btnSubscribe.setOnClickListener {
            val intent = Intent(context, ConfirmDeliveryAddress::class.java)
            intent.putExtra("menuId", menuId)
            intent.putExtra("mealPlanId", mealPlanId)
            intent.putExtra("menuSlotId", menuSlotId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvDescription: TextView
        var tvConisDesc: TextView
        var btnSubscribe: Button

        init {
            tvDescription = itemView.findViewById(R.id.tvDescription) as TextView
            tvConisDesc = itemView.findViewById(R.id.tvConisDesc) as TextView
            btnSubscribe = itemView.findViewById(R.id.btnSubscribe) as Button
        }
    }
}