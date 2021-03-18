package com.app.tiffinapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.app.tiffinapp.utils.Utility
import com.app.tiffinapp.utils.WMPreference
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.addresslist_activity.*
import kotlinx.android.synthetic.main.confirm_delivery_address.*
import kotlinx.android.synthetic.main.confirm_delivery_address.drawer_layout
import kotlinx.android.synthetic.main.delivery_boys.*
import kotlinx.android.synthetic.main.subscription_order.*
import kotlinx.android.synthetic.main.toolbar.*

class ConfirmDeliveryAddress : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    var mContext: Context? = null
    var menuId: String = ""
    var mealPlanId: String = ""
    var menuSlotId: String = ""
    var navigationView: NavigationView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_delivery_address)
        mContext = this

        menuId = intent.getStringExtra("menuId")!!
        mealPlanId = intent.getStringExtra("mealPlanId")!!
        menuSlotId = intent.getStringExtra("menuSlotId")!!

        navigationView = findViewById(R.id.nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.itemIconTintList = null

        btn_menu!!.setOnClickListener(this)

        if(WMPreference.get_userType(mContext!!)!! == "O" || WMPreference.get_userType(mContext!!)!! == "D"){
            val nav_Menu = navigationView!!.getMenu()
            nav_Menu.findItem(R.id.nav_address).isVisible = false
        }

        val styledText = "<font color='#ffffff'>Don't have a profile address?</font>."
        val styledText1 = "<font color='#ffa922'>Add Address in profile</font>."

        tvAddresspage.setText(Html.fromHtml(styledText + styledText1, Html.FROM_HTML_MODE_COMPACT));

        btnSubscribe.setOnClickListener {
            val intent = Intent(this, PaymentOptionActivity::class.java)
            intent.putExtra("menuId", menuId)
            intent.putExtra("mealPlanId", mealPlanId)
            intent.putExtra("menuSlotId", menuSlotId)
            startActivity(intent)
        }

        tvProfileRedirect.setOnClickListener {

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        Utility.openNavDrawer(id, mContext!!)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        if (v == btn_menu) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }
}