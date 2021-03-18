package com.app.tiffinapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.AddressListModel
import com.app.tiffinapp.models.SubscriptionZoneList

class AddressListAdapter(
    val userList: List<AddressListModel.AddressList>?,
    private val onNewsSelected: onRowItemSelected
) :
    RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {
    lateinit var rowView: View

    interface onRowItemSelected {
        fun getPosition(id: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.row_address, parent, false)
        rowView = itemView
        return ViewHolder(itemView)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = "Name: "+userList!!.get(position).firstName+" "+userList!!.get(position).lastName
        holder.tvAddress.text = userList!!.get(position).addressLine1
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList!!.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvAddress: TextView
        var mainBody: LinearLayout
        var tvID: TextView

        init {
            tvAddress = itemView.findViewById(R.id.tvAddress) as TextView
            tvName = itemView.findViewById(R.id.tvName) as TextView
            mainBody = itemView.findViewById(R.id.mainBody) as LinearLayout
            tvID = itemView.findViewById(R.id.tvID) as TextView
        }
    }
}