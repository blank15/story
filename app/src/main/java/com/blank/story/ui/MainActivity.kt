package com.blank.story.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.blank.domain.repository.AuthRepository
import com.blank.story.R
import com.blank.story.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private val auhtRepository by inject<AuthRepository>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater

        val graph =  if(auhtRepository.isLogin)  inflater.inflate(com.blank.navigation.R.navigation.nav_graph_main)
        else inflater.inflate(com.blank.navigation.R.navigation.nav_graph_auth)

        navHostFragment.navController.graph = graph
    }
}