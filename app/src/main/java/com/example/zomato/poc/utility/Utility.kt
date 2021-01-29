package com.example.zomato.poc.utility

import android.content.Context
import android.widget.Toast

object Utility {
    fun toast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}