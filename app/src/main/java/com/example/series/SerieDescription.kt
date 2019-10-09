package com.example.series

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_serie_description.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class SerieDescription : AppCompatActivity() {

    private var jsonResponse = ""
    private val client = OkHttpClient()

    private var name = ""
    private var status = ""
    private var imageURL = ""
    private var summary = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_description)

        val mIntent = intent
        val id = mIntent.getStringExtra("id")

        run("http://api.tvmaze.com/shows/" + id.toString())
        txtNameD.text = name
        txtStatusD.text = status
        txtDescriptionD.text = summary
    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                jsonResponse = response.body()?.string().toString()
                println(jsonResponse)
                formatJson(jsonResponse.toString())
            }
        })
    }


    fun updateData(){
        val mono : ImageView = findViewById(R.id.imgCoverD)
        txtNameD.text = name
        txtStatusD.text = status
        Handler(Looper.getMainLooper()).post {
            Picasso.get()
                .load(imageURL)
                .into(mono)

            val mono : TextView = findViewById(R.id.txtDescriptionD)
            mono.setVisibility(View.GONE)
            mono.setVisibility(View.VISIBLE)

            txtDescriptionD.text = summary
        }

    }

    private fun formatJson(jsonSTR : String){
        try {
            val json = JSONObject(jsonSTR)
            name = json.getString("name")
            status = json.getString("status")
            val imageObj = json.getJSONObject("image")
            imageURL = imageObj.getString("medium")
            summary = json.getString("summary")
            updateData()


        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
