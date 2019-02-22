package com.jchavez.wheelycoolapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jchavez.wheelycoolapp.R
import com.jchavez.wheelycoolapp.fragments.SpinWheelFragment
import com.jchavez.wheelycoolapp.fragments.WheelOptionFragment
import com.jchavez.wheelycoolapp.models.WheelOption

class MainActivity : AppCompatActivity(), WheelOptionFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val wheelOptionFragment = WheelOptionFragment.newInstance(1)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.content_frame_layout, wheelOptionFragment)
            transaction.commit()
        }
    }

    override fun onListFragmentInteraction(item: WheelOption?) {
    }

    override fun onContinueFragmentInteraction(items: ArrayList<WheelOption>) {
        val spinWheelFragment = SpinWheelFragment.newInstance(items)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame_layout, spinWheelFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.add -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.wheel_options_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
