package com.example.monitoringapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.monitoringapp.data.model.User
import com.example.monitoringapp.data.network.request.UpdatePasswordRequest
import com.example.monitoringapp.databinding.ActivityChangePasswordBinding
import com.example.monitoringapp.util.Constants
import com.example.monitoringapp.util.UIViewState
import com.example.monitoringapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : AppCompatActivity() {

    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dni = intent.getStringExtra("dni")
        setupObservers()

        binding.run {
            buttonAccept.setOnClickListener {
                if (!editCurrentPassword.text.isNullOrEmpty() &&
                    !editNewPassword.text.isNullOrEmpty() &&
                    !editNewRepassword.text.isNullOrEmpty()
                ) {
                    if (editNewPassword.text.toString() == editNewRepassword.text.toString()) {
                        val updatePasswordRequest = UpdatePasswordRequest()
                        updatePasswordRequest.identification = dni!!
                        updatePasswordRequest.oldPassword = editCurrentPassword.text.toString()
                        updatePasswordRequest.newPassword = editNewPassword.text.toString()
                        authenticationViewModel.updatePassword(updatePasswordRequest)
                    } else {
                        toast("La nueva contraseña y la confirmación no son iguales")
                    }
                } else {
                    toast("Completar todos los campos")
                }
            }
        }

    }

    private fun setupObservers() {
        authenticationViewModel.uiViewUpdatePasswordStateObservable.observe(
            this@ChangePasswordActivity,
            updatePasswordObserver
        )
    }

    //Observer
    private val updatePasswordObserver = Observer<UIViewState<String>> {
        when (it) {
            is UIViewState.Success -> {
                showAlertDialog()
            }
            is UIViewState.Error -> {
                toast(Constants.DEFAULT_ERROR)
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Se cambio la contraseña")
        builder.setNeutralButton("Ok") { _, _ ->  finish()}
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}