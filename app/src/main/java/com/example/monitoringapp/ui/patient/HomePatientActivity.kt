package com.example.monitoringapp.ui.patient

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.ActivityHomePatientBinding

class HomePatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePatientBinding

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePatientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            actionBarDrawerToggle = ActionBarDrawerToggle(
                this@HomePatientActivity,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )

            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}