package com.manoffocus.mfrickandmorty.commons

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    companion object {
        fun convertArrayToStringFormat(arr: Array<Int>): String{
            return "[" + arr.joinToString(separator = ",") + "]"
        }
        @SuppressLint("SimpleDateFormat")
        fun formatDateToHumans(date: Date): String {
            val format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(date)
        }
    }
}