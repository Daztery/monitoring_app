package com.example.monitoringapp.ui.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.ActivityHomeDoctorBinding
import com.example.monitoringapp.databinding.ActivityHomePatientBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDoctorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeDoctorBinding

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {

            actionBarDrawerToggle = ActionBarDrawerToggle(
                this@HomeDoctorActivity,
                drawerLayout,
                R.string.nav_open,
                R.string.nav_close
            )

            drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

            navigationView.setNavigationItemSelectedListener(this@HomeDoctorActivity)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.i("Selected",item.itemId.toString())
        return true
    }


}