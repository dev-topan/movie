package com.topan.presentation.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.topan.presentation.R

/**
 * Created by Topan E on 26/03/23.
 */
object Dialog {
    fun show(context: Context, message: String, action: () -> Unit = {}) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { _, _ ->
            action.invoke()
        }
        val dialog = builder.create()
        setStyle(dialog)
        dialog.show()
    }

    private fun setStyle(dialog: AlertDialog) {
        dialog.window?.setBackgroundDrawableResource(R.color.dark_gray)
        dialog.setOnShowListener {
            val messageTextView = dialog.findViewById<TextView>(android.R.id.message)
            messageTextView?.setTextColor(Color.WHITE)
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)
            positiveButton?.setTextColor(Color.WHITE)
        }
    }
}
