package com.example.yemek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Toolbar'ı bulma ve ayarlama
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.yemek_ekle,menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.yemek_ekleme_item -> {
                try {
                    val action = ListeFragmentDirections.actionListeFragmentToTarifFragment()
                    Navigation.findNavController(this, R.id.fragmentContainerView).navigate(action)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}