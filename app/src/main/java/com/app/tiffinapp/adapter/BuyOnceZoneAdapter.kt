/*
package com.app.tiffinapp.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.tiffinapp.R
import com.app.tiffinapp.models.BuyOnceZoneList
import com.app.tiffinapp.models.SubscriptionZoneList

class BuyOnceZoneAdapter
internal constructor(
    private val context: Context,
    private val dataList: List<BuyOnceZoneList.MealplansList>,
    private val onNewsSelected: BuyOnceZoneAdapter.onRowItemSelected
) : BaseExpandableListAdapter() {

    interface onRowItemSelected {
        fun getPosition(id: String)
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        //return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
        return this.dataList[listPosition].menuItemsList!![expandedListPosition]

    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        // val expandedListText = getChild(listPosition, expandedListPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.expandedListItem)
        expandedListTextView.text =
            dataList.get(listPosition).menuItemsList?.get(expandedListPosition)!!.itemsName
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
*/
/*
        return this.dataList[this.titleList[listPosition]]!!.size
*//*

        return this.dataList.get(listPosition).menuItemsList!!.size

    }

    override fun getGroup(listPosition: Int): Any {
*/
/*
        return this.titleList[listPosition]
*//*

        return this.dataList[listPosition]

    }

    override fun getGroupCount(): Int {
*/
/*
        return this.titleList.size
*//*

        return this.dataList.size

    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        //val listTitle = getGroup(listPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.listTitle)
        //listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text =
            dataList.get(listPosition).mealPlansName + " - " + dataList.get(listPosition).mealSlotName
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }


}*/
