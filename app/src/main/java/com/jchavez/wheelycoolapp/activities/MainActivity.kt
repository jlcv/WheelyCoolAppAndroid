package com.jchavez.wheelycoolapp.activities

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.jchavez.wheelycoolapp.R
import com.jchavez.wheelycoolapp.fragments.InputFragment
import com.jchavez.wheelycoolapp.fragments.SpinWheelFragment
import com.jchavez.wheelycoolapp.fragments.WheelOptionFragment
import com.jchavez.wheelycoolapp.models.WheelOption
import com.jchavez.wheelycoolapp.utils.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(),
        WheelOptionFragment.OnListFragmentInteractionListener,
        InputFragment.OnFragmentDialogInteractionListener {

    private var wheelOptionFragment: WheelOptionFragment? = null
    private var database: RoomDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, getString(R.string.database_name)
            ).build()
            database?.let {
                doAsync {
                    val items = (it as AppDatabase).wheelOptionDao().getAll()
                    uiThread {
                        wheelOptionFragment = WheelOptionFragment.newInstance(1,
                                ArrayList(items))
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.add(R.id.content_frame_layout, wheelOptionFragment)
                        transaction.commit()
                    }
                }
            }
        }
    }

    override fun onListFragmentInteraction(item: WheelOption) {
        doAsync {
            database?.let {
                (it as AppDatabase).wheelOptionDao().delete(item)
            }
            uiThread {
                wheelOptionFragment?.removeWheelOption(item)
            }
        }
    }

    override fun onContinueFragmentInteraction(items: ArrayList<WheelOption>) {
        val spinWheelFragment = SpinWheelFragment.newInstance(items)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame_layout, spinWheelFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onFragmentDialogInteraction(itemName: String) {
        val wheelOption = WheelOption(itemName)
        doAsync {
            database?.let {
                (it as AppDatabase).wheelOptionDao().insert(wheelOption)
            }
            uiThread {
                wheelOptionFragment?.addWheelOption(wheelOption)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.add -> {
                val numberOfItems = wheelOptionFragment?.numberOfItems() ?: 0
                if (numberOfItems < 10) {
                    val inputDialogFragment = InputFragment.newInstance()
                    inputDialogFragment.show(supportFragmentManager, "inputFragmentDialog")
                } else {
                    Toast.makeText(this@MainActivity, getString(R.string.too_many_items),
                            Toast.LENGTH_SHORT).show()
                }
                return true
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
