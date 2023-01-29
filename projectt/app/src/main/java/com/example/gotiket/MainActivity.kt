package com.example.gotiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gotiket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id3 = intent.getStringExtra("ids").toString()
        val username3 = intent.getStringExtra("usernames").toString()

        println(id3.toString()+" "+username3.toString())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.btn_pesawat -> replaceFragment(caripesawat(id3,username3))
                R.id.btnkereta -> replaceFragment(carikereta(id3,username3))
                R.id.btnbelanja -> replaceFragment(Belanja(id3,username3))
                R.id.btnhistory -> replaceFragment(History(id3,username3))
                R.id.btnprofile -> replaceFragment(Profile(id3,username3))
                else -> replaceFragment(Profile(id3,username3))
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}