package com.app.tiffinapp.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.BuyOnceZoneList


class BuyOnceZoneRecyclerAdapter
internal constructor(
    private val context: Context,
    private val dataList: List<BuyOnceZoneList.MealplansList>,
    private val onNewsSelected: onRowItemSelected
) :
    RecyclerView.Adapter<BuyOnceZoneRecyclerAdapter.ViewHolder>() {
    lateinit var rowView: View

    interface onRowItemSelected {
        fun getPosition(menuId: String, mealPlanId: String, menuSlotId: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(
            R.layout.buyonce_main,
            parent,
            false
        )
        rowView = itemView
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataList!![position].mealPlansName

        val buttonLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        buttonLayoutParams.setMargins(20, 0, 8, 0)
        var layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


        for (i in 0..dataList[position].menulList!!.size) {
            val view1: View = layoutInflater.inflate(R.layout.buyonce_inflater, null)
            try {
                var listTitle = view1.findViewById(R.id.listTitle) as TextView
                var llContainerChild = view1.findViewById(R.id.llContainerChild) as LinearLayout
                var tvPrice = view1.findViewById(R.id.tvPrice) as TextView
                var btnBuyonce = view1.findViewById(R.id.btnBuyonce) as TextView
                listTitle.text = dataList[position].menulList!![i].mealSlotName
                llContainerChild.removeAllViews()

                var MealPlansIDtv = view1.findViewById(R.id.MealPlansID) as TextView
                var MenuIDtv = view1.findViewById(R.id.MenuID) as TextView
                var MealSlotIDtv = view1.findViewById(R.id.MealSlotID) as TextView
                MealPlansIDtv.text = dataList[position].mealPlansID
                MenuIDtv.text = dataList[position].menulList!![i].menuID!!
                MealSlotIDtv.text = dataList[position].menulList!![i].MealSlotID!!
                tvPrice.text =
                    "\u20B9 " + dataList!![position].menulList!![i].menuPrice + "  / Coins " + dataList!![position].menulList!![i].menuCoins

                if (dataList[position].menulList!![i].menuItemsList!!.isNotEmpty()) {
                    try {
                        val myTextViews =
                            arrayOfNulls<TextView>(dataList[position].menulList!![i].menuItemsList!!.size) // create an empty array;

                        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(0, 0, 0, 5)
                        for (j in 0..dataList[position].menulList!![i].menuItemsList!!.size) {
                            val rowTextView = TextView(context)
                            var k = j + 1
                            rowTextView.setText("$k. " + dataList[position].menulList!![i].menuItemsList!![j].itemsName!!);
                            rowTextView.setTextColor(Color.parseColor("#ffffff"))
                            rowTextView.layoutParams = params
                            rowTextView.setTypeface(null, Typeface.BOLD);
                            myTextViews[j] = rowTextView
                            llContainerChild.addView(myTextViews[j])
                        }
                    } catch (e: Exception) {
                    }
                }

                btnBuyonce.setOnClickListener {
                    onNewsSelected.getPosition(
                        MenuIDtv.text.toString(),
                        MealPlansIDtv.text.toString(),
                        MealSlotIDtv.text.toString()
                    )
                }

                holder.llContainer.addView(view1)
            } catch (e: Exception) {

            }
        }
        // }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return dataList!!.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var llContainer: LinearLayout
        var tvID: TextView

        init {
            title = itemView.findViewById(R.id.title) as TextView
            llContainer = itemView.findViewById(R.id.llContainer) as LinearLayout
            tvID = itemView.findViewById(R.id.tvID) as TextView
        }

    }
}