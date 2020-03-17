package com.coding.stackexchange

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Base Activity class that helps in managing the indentical operations required by multiple
 * Activities in the Application.
 */
abstract class BaseActivity : AppCompatActivity() {

    // Broadcast Receiver to check for the network connectivity.
    private val networkState = NetworkStateReceiver()
    private val networkFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")

    // Boolean to inform whether the internet connectivity is available at the moment.
    private var isNetworkAvailable: Boolean = true

    /**
     * Function to notify the current state of the network connectivity.
     */
    abstract fun getNetworkAvailability(isConnected: Boolean)

    override fun onResume() {
        super.onResume()
        registerReceiver(networkState, networkFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkState)
    }

    /**
     * disable the view from touchable
     *
     */
    open fun disableView() {
        this.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    /**
     * Enable the view for touchable
     *
     */
    open fun enableView() {
        this.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    /**
     * Function to return the current state of the network connectivity.
     */
    fun getNetworkState() = isNetworkAvailable

    /**
     * [BroadcastReceiver] class to listen to the network state.
     */
    inner class NetworkStateReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            context?.run {
                val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                val isConnected: Boolean = activeNetwork?.isConnected == true

                getNetworkAvailability(isConnected)
                isNetworkAvailable = isConnected
            }
        }
    }
}