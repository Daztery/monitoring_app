package com.example.monitoringapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.monitoringapp.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {


    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAccept.setOnClickListener {
            showAlertDialog()
        }

    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Se cambio la contraseña")

        builder.setNeutralButton("Ok") { _, _ -> }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}