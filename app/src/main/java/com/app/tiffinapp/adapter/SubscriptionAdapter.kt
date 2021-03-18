package com.app.tiffinapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.SubscriptionZoneList

class SubscriptionAdapter(
    val userList: List<SubscriptionZoneList.MealplansList>?,
    private val onNewsSelected: onRowItemSelected
) :
    RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {
    lateinit var rowView: View

    interface onRowItemSelected {
        fun getPosition(id: String)
    }


    /*override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubscriptionAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.row_subscription, parent, false)
        return ViewHolder(v)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
            .inflate(R.layout.row_subscription, parent, false)

        rowView = itemView
        return ViewHolder(itemView)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.bindItems(userList!![position])
        holder.textViewName.text = userList!!.get(position).mealPlansName
        holder.tvID.text = userList!!.get(position).mealPlansID

        holder.mainBody.setOnClickListener {
            onNewsSelected.getPosition(holder.tvID.text.toString())
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList!!.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView
        var mainBody: LinearLayout
        var tvID: TextView

        init {
            textViewName = itemView.findViewById(R.id.tvHeader) as TextView
            mainBody = itemView.findViewById(R.id.mainBody) as LinearLayout
            tvID = itemView.findViewById(R.id.tvID) as TextView
        }

    }
}