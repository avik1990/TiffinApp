package com.app.tiffinapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.AllotedDeliveryModel

class DeliveryStatusAdapter(val userList: ArrayList<AllotedDeliveryModel>) :
    RecyclerView.Adapter<DeliveryStatusAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryStatusAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_deliverystatus, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: DeliveryStatusAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        /*return userList.size*/
        return 18
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: AllotedDeliveryModel) {
            val textViewName = itemView.findViewById(R.id.tvName) as TextView
           /* val textViewAddress = itemView.findViewById(R.id.tvAddress) as TextView
            val textViewMobile = itemView.findViewById(R.id.tvMobile) as TextView*/


            textViewName.text = "Sanjoy Doe"
            /*textViewAddress.text = "Dum DUm"
            textViewMobile.text = "95433435957"*/

            //textViewName.text = user.name
            //textViewAddress.text = user.address
        }
    }
}