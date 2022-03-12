package com.example.monitoringapp.ui.patient

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.ActivityHomePatientBinding
import com.example.monitoringapp.ui.StartActivity
import com.example.monitoringapp.ui.patient.alerts.AlertsFragment
import com.example.monitoringapp.ui.patient.dailyreport.DailyReportFragment
import com.example.monitoringapp.ui.patient.home.HomePatientFragment
import com.example.monitoringapp.ui.patient.medicalhistory.MedicalHistoryFragment
import com.example.monitoringapp.ui.patient.medicalrecord.MedicalRecordFragment
import com.example.monitoringapp.ui.patient.prescription.PrescriptionFragment
import com.example.monitoringapp.ui.patient.profile.ProfileFragment
import com.example.monitoringapp.util.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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


            replaceFragment(HomePatientFragment())
            navigationView.setNavigationItemSelectedListener {
                it.isChecked = true
                when (it.itemId) {
                    R.id.nav_profile_fragment -> replaceFragment(ProfileFragment())
                    R.id.nav_history_doctor_fragment -> replaceFragment(MedicalRecordFragment())
                    R.id.nav_medical_record_fragment -> replaceFragment(MedicalHistoryFragment())
                    R.id.nav_daily_report_fragment -> replaceFragment(DailyReportFragment())
                    R.id.nav_alerts_fragment -> replaceFragment(AlertsFragment())
                    R.id.nav_prescription_fragment -> replaceFragment(PrescriptionFragment())
                    R.id.logout_patient -> {
                        PreferencesHelper.clear()
                        val intent = Intent(this@HomePatientActivity, StartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                true
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.run {
            drawerLayout.openDrawer(navigationView)
            return true
        }
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_patient_fragment, fragment)
        fragmentTransaction.commit()
        this.binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

}