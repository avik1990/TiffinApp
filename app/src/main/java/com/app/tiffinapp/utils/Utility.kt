@file:JvmName("Utility")
@file:JvmMultifileClass

package com.app.tiffinapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.widget.Toast
import com.app.tiffinapp.AddressList
import com.app.tiffinapp.LoginActivity
import com.app.tiffinapp.MyProfile
import com.app.tiffinapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utility {

    var cd: ConnectionDetector? = null
    var PACKAGE_NAME: String = ""

    fun openNavDrawer(id: Int, mContext: Context) {
        cd = ConnectionDetector(mContext)
        if (id == R.id.nav_myprofile) {
            if (cd!!.isConnected()) {
                if (mContext !is MyProfile) {
                    val profileintent = Intent(mContext, MyProfile::class.java)
                    mContext.startActivity(profileintent)
                }
            } else {
                showToastShort(mContext, "No Internet")
            }
        }else if (id == R.id.nav_address) {
            if (cd!!.isConnected()) {
                if (mContext !is AddressList) {
                    val profileintent = Intent(mContext, AddressList::class.java)
                    mContext.startActivity(profileintent)
                }
            } else {
                showToastShort(mContext, "No Internet")
            }
        }

        if (id == R.id.nav_logout) {
            showLogoutAlert(mContext, "Are you sure?", "Logout")
        }


    }

    fun showLogoutAlert(context: Context, msg: String, title: String) {
        val builder = android.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("OK") { dialog, which ->
            val profileintent = Intent(context, LoginActivity::class.java)
            context.startActivity(profileintent)
            (context as Activity).finishAffinity()
            cleardata(context)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }

    fun showToastShort(mContext: Context, msg: String) {
        val toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun shareAll(mContext: Context, heading: String, sub: String, links: String) {
        //String title = heading;
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, links)
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, heading)
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share Using"))
    }


    /*fun showLogoutAlert(context: Context, msg: String, title: String) {
        val builder = android.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("OK") { dialog, which ->
            val profileintent = Intent(context, LoginActivity::class.java)
            context.startActivity(profileintent)
            (context as Activity).finishAffinity()
            cleardata(context)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.dismiss() }
        builder.show()
    }*/

    fun cleardata(mContext: Context) {
        //DatabaseHandler db = new DatabaseHandler(mContext);
        val settings = mContext.getSharedPreferences("Kppref", Context.MODE_PRIVATE)
        settings.edit().clear().apply()
        // db.drop_all_data();
    }


    fun getFormattedDate(normal_date: String): String {
        Log.d("DateFormat", normal_date)
        var formated_date = ""
        if (normal_date.length > 6) {
            val originalFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
            // originalFormat.timeZone = TimeZone.getTimeZone("GMT")
            val targetFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date
            try {
                date = originalFormat.parse(normal_date.trim())
                formated_date = targetFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        } else {
            formated_date = normal_date
        }
        return formated_date
    }
}