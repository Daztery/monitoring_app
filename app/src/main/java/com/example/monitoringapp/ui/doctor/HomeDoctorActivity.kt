package com.example.monitoringapp.ui.doctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.monitoringapp.R
import com.example.monitoringapp.databinding.ActivityHomeDoctorBinding
import com.example.monitoringapp.ui.StartActivity
import com.example.monitoringapp.ui.doctor.alerts.AlertsDoctorFragment
import com.example.monitoringapp.ui.doctor.historyclinic.ElectronicMedicalRecordFragment
import com.example.monitoringapp.ui.doctor.home.HomeDoctorFragment
import com.example.monitoringapp.ui.doctor.patientsemergency.PatientsEmergencyFragment
import com.example.monitoringapp.ui.doctor.patientspriority.PatientsPriorityFragment
import com.example.monitoringapp.ui.doctor.patientstatus.PatientStatusFragment
import com.example.monitoringapp.ui.doctor.registermonitoringplan.RegisterMonitoringPlanFragment
import com.example.monitoringapp.ui.doctor.registerpatient.RegisterPatientFragment
import com.example.monitoringapp.ui.doctor.reports.ReportsFragment
import com.example.monitoringapp.ui.doctor.searchpatient.SearchPatientFragment
import com.example.monitoringapp.util.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDoctorActivity : AppCompatActivity() {

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

            replaceFragment(HomeDoctorFragment())

            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_reports_fragment -> replaceFragment(ReportsFragment())
                    R.id.nav_patient_status_fragment -> replaceFragment(PatientStatusFragment())
                    R.id.nav_patients_emergency_type -> replaceFragment(PatientsEmergencyFragment())
                    R.id.nav_patients_priority -> replaceFragment(PatientsPriorityFragment())
                    R.id.nav_alerts_doctor_fragment -> replaceFragment(AlertsDoctorFragment())
                    R.id.nav_history_clinic -> replaceFragment(ElectronicMedicalRecordFragment())
                    R.id.nav_search_patient_fragment -> replaceFragment(SearchPatientFragment())
                    R.id.nav_register_plan_fragment -> replaceFragment(
                        RegisterMonitoringPlanFragment()
                    )
                    R.id.nav_register_patient_fragment -> replaceFragment(RegisterPatientFragment())
                    R.id.logout_doctor -> {
                        PreferencesHelper.clear()
                        val intent = Intent(this@HomeDoctorActivity, StartActivity::class.java)
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
        fragmentTransaction.replace(R.id.nav_doctor_fragment, fragment)
        fragmentTransaction.commit()
        this.binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

}