package com.app.tiffinapp

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.adapter.DeliveryBoysAdapter
import com.app.tiffinapp.adapter.SubscriptionOrderAdapter
import com.app.tiffinapp.models.AllotedDeliveryModel
import com.app.tiffinapp.models.SubscriptionOrderResponse
import com.app.tiffinapp.utils.Utility
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.subscription_order.*
import kotlinx.android.synthetic.main.toolbar.*

class SubscriptionOrder : AppCompatActivity(), View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    var mContext: Context? = null
    var users = ArrayList<SubscriptionOrderResponse>()

    var menuId: String = ""
    var mealPlanId: String = ""
    var menuSlotId: String = ""
    var navigationView: NavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subscription_order)
        mContext = this

        menuId=intent.getStringExtra("menuId")!!
        mealPlanId=intent.getStringExtra("mealPlanId")!!
        menuSlotId=intent.getStringExtra("menuSlotId")!!
        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null
        btn_menu!!.setOnClickListener(this)

        users.add(
            SubscriptionOrderResponse(
                "Tiffin box will be provided to aapan rasaoi tiffins by you \n 2pieces mandatory for smooth services",
                ".......coins(monthly)"
            )
        )
        users.add(
            SubscriptionOrderResponse(
                "You are accepting to receive food \n in 5 cp plastic tray",
                ".......coins(monthly)"
            )
        )

        inflateAdapter()
    }

    private fun inflateAdapter() {
        val adapter = SubscriptionOrderAdapter(users,mContext!!,menuId,mealPlanId,menuSlotId)
        //now adding the adapter to recyclerview
        rvSubscriptionOrder.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_menu -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, mContext!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}