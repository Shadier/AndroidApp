package com.example.series

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_series_list.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

private val client = OkHttpClient()

class SeriesList : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var jsonResponse = ""
    private var rootView : View? = null

    var seriesList : ArrayList<Serie>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun formatJson(jsonSTR : String) {
        try {
            seriesList = ArrayList()
            val series = JSONArray(jsonSTR)
            for (i in 0 until series.length()){
                val serie = series.getJSONObject(i)
                val name = serie.getString("name")
                val id = serie.getInt("id").toString()
                val imgURL = serie.getJSONObject("image").getString("medium")
                val status = serie.getString("status")
                seriesList?.add(Serie(id, name, imgURL, status))


            }



            Handler(Looper.getMainLooper()).post {
                var list = rootView?.findViewById<RecyclerView>(R.id.recyclerList)
                var layoutManager = LinearLayoutManager(context)
                list?.layoutManager = layoutManager
                list?.adapter = Adapter(seriesList!!, object : ClickListener {
                    override fun onClic(view: View, id: Int) {
                        Toast.makeText(context, seriesList?.get(id)?.id, Toast.LENGTH_LONG).show()
                        val sendData = Intent(context, SerieDescription::class.java).apply {
                            putExtra("id", seriesList?.get(id)?.id)
                        }
                        startActivity(sendData)
                    }
                })
            }

        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun run() {
        val request = Request.Builder()
            .url("http://api.tvmaze.com/shows")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                jsonResponse = response.body()?.string().toString()
                println(jsonResponse)
                formatJson(jsonResponse)
            }
        })
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_series_list, container, false)


        run()




        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

}
