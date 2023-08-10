package com.example.movieapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.TypedValue
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Utils {

    fun dpToPx(context: Context, dp: Number): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).roundToInt();
    }

    fun getYearFromDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.YEAR).toString()
    }
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun getDuration( min: Int) : String{
        var sdf = SimpleDateFormat("mm")

        try {
            val dt = sdf.parse(min.toString())
            sdf = SimpleDateFormat("HH:mm")
             return  sdf.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

}