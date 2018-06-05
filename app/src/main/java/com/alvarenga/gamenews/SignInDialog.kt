package com.alvarenga.gamenews

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View

class SignInDialog():AppCompatDialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder:AlertDialog.Builder = AlertDialog.Builder(activity!!)

        var inflater:LayoutInflater = activity!!.layoutInflater
        var view:View = inflater.inflate(R.layout.layout_dialog_login,null)

        return builder.create()
    }
}