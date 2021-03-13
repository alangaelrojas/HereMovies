package com.alan.heremovies.sys.utils.broadcast_receiver

interface OnNetworkChange {

    fun hasInternet(hasConnection: Boolean)
}