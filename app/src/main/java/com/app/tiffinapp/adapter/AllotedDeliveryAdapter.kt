package com.app.tiffinapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.DeliveryStatusListingResponse

class AllotedDeliveryAdapter(
    val context: Context,
    val userList: List<DeliveryStatusListingResponse.DeliveryStatusList>
) :
    RecyclerView.Adapter<AllotedDeliveryAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllotedDeliveryAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_alloted_delivery, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text =
            userList.get(position).firstName + " " + userList.get(position).lastName
        //holder.tvAddress.text = userList.get(position).
        holder.tvMobile.text = userList.get(position).mobileNumber
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }


    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvAddress: TextView
        var tvMobile: TextView
        var btnStatus: Button


        init {
            tvName = itemView.findViewById(R.id.tvName) as TextView
            tvAddress = itemView.findViewById(R.id.tvAddress) as TextView
            tvMobile = itemView.findViewById(R.id.tvMobile) as TextView
            btnStatus = itemView.findViewById(R.id.btnStatus) as Button

            //mainBody = itemView.findViewById(R.id.mainBody) as LinearLayout
            // tvID = itemView.findViewById(R.id.tvID) as TextView
        }
    }


}