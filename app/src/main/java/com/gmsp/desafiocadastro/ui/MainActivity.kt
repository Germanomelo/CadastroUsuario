package com.gmsp.desafiocadastro.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmsp.desafiocadastro.R
import com.gmsp.desafiocadastro.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.inflateMenu(R.menu.toolbar_menu)
    }

}