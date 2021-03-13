package com.alan.heremovies.sys.utils.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alan.heremovies.sys.utils.hasConnection
import java.lang.Exception

class NetworkStateReceiver(
    private val onNetworkChange: OnNetworkChange
    ) : BroadcastReceiver() {

    override fun onReceive(c: Context?, i: Intent?) {
        try {
            c?.hasConnection()?.let { onNetworkChange.hasInternet(it) }
        } catch (ex: Exception){
            ex.printStackTrace()
            onNetworkChange.hasInternet(false)
        }
    }
}