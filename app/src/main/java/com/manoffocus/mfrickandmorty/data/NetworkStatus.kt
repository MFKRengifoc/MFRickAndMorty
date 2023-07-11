package com.manoffocus.mfrickandmorty.data

import android.content.Context
import com.manoffocus.mfrickandmorty.R

interface NetWorkStatusIF{
    fun getStatus(ctx: Context): Pair<String, Boolean>
}

sealed class NetworkStatus: NetWorkStatusIF {
    object Unknown: NetworkStatus(){
        override fun getStatus(ctx: Context): Pair<String, Boolean> {
            return Pair(
                ctx.getString(R.string.mf_home_screen_unknown_label),
                false
            )
        }
    }
    object Connected : NetworkStatus(){
        override fun getStatus(ctx: Context): Pair<String, Boolean> {
            return Pair("", true)
        }
    }
    object Disconnected: NetworkStatus(){
        override fun getStatus(ctx: Context): Pair<String, Boolean> {
            return Pair(
                ctx.getString(R.string.mf_home_screen_disconnected_label),
                false
            )
        }
    }
}