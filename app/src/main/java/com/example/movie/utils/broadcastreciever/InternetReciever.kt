package com.example.movie.utils.broadcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class InternetReciever: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(!isInternet(context)){
            showNoInternetToast(context)
        }else{
            Toast.makeText(context, "You are online", Toast.LENGTH_LONG).show()
        }
    }

    private fun isInternet(context: Context?): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capability = connectivityManager.getNetworkCapabilities(network)
        return capability != null && capability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showNoInternetToast(context: Context?){
        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show()
    }
}