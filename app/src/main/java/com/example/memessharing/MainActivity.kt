package com.example.memessharing

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageurl: String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()
    }

    private fun loadmeme(){
        val progressBar = findViewById<ProgressBar>(R.id.loadBar) as ProgressBar
        progressBar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val memeImageView = findViewById<ImageView>(R.id.memeImageView)
   val jsonObjectRequest=JsonObjectRequest(
            Request.Method.GET, url, null,
       { response ->
           // Display the first 500 characters of the response string.
        currentImageurl=response.getString("url")

           Glide.with(this).load(currentImageurl).listener(object : RequestListener<Drawable> {
               override fun onLoadFailed(
                   e: GlideException?,
                   model: Any?,
                   target: Target<Drawable>?,
                   isFirstResource: Boolean
               ): Boolean {
                   progressBar.visibility=View.GONE
                   return false
               }

               override fun onResourceReady(
                   resource: Drawable?,
                   model: Any?,
                   target: Target<Drawable>?,
                   dataSource: DataSource?,
                   isFirstResource: Boolean
               ): Boolean {
                   progressBar.visibility=View.GONE
                   return false
               }

           }).into(memeImageView)

       },
       {

       })



// Add the request to the RequestQueue.
       queue.add(jsonObjectRequest)
       // MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun sharefunc(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"$currentImageurl")
        val chooser=Intent.createChooser(intent," share this using ...")
        startActivity(chooser)
    }
    fun nextfunc(view: View) {
        loadmeme()
    }
}