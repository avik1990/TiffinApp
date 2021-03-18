package com.app.tiffinapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.AllotedDeliveryModel
import com.app.tiffinapp.models.DeliveryBoysModel

class DeliveryBoysAdapter(
    val context: Context,
    val userList: List<DeliveryBoysModel.DeliveryBoyList>
) : RecyclerView.Adapter<DeliveryBoysAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_deliveryboys, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: DeliveryBoysAdapter.ViewHolder, position: Int) {
        holder.tvName.text = userList!!.get(position).firstName + " " + userList!!.get(position).lastName
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        //var mainBody: LinearLayout
        // var tvID: TextView
        var radioGroup: RadioGroup

        init {
            tvName = itemView.findViewById(R.id.tvName) as TextView
            radioGroup = itemView.findViewById(R.id.radioGroup) as RadioGroup
            //mainBody = itemView.findViewById(R.id.mainBody) as LinearLayout
            // tvID = itemView.findViewById(R.id.tvID) as TextView
        }
    }
}