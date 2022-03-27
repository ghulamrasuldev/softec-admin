package com.example.softecadminapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.softecadminapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addStall = AddStallFragment()
        val addCompetition = AddCompetitionFragment()
        binding.addStall.setOnClickListener {
            addStall.show(supportFragmentManager, "bottom sheet dialogue")
        }


        binding.addCompetition.setOnClickListener{
            addCompetition.show(supportFragmentManager, "bottom sheet dialogue")
        }

        binding.paymentRequests.setOnClickListener{
            val intent = Intent(this, ReviewPayments::class.java)
            startActivity(intent)
        }
    }
}