package com.example.series

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_series_list.*
import java.net.URI

class MainActivity : AppCompatActivity() , SeriesList.OnFragmentInteractionListener, AboutUs.OnFragmentInteractionListener {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnSeries.setOnClickListener {
            openFragment(SeriesList())
        }
        btnAboutUs.setOnClickListener {
            openFragment(AboutUs())
        }
    }

    fun openFragment(fragment : Fragment){
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.MainFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onFragmentInteraction(uri: Uri) {
    }

}
