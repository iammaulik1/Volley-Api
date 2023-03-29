package com.example.volleyapi

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.volleyapi.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url :String = "https://meme-api.com/gimme"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getmemeData()

        binding.buttonNewMeme.setOnClickListener {
            getmemeData()
        }

    }

    fun getmemeData(){

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait While Data Is Fetch")
        progressDialog.show()

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.e("Response","getMemeData: " +response.toString())

                var responseObject = JSONObject(response)

                responseObject.get("url")
                responseObject.get("postLink")

                binding.memeTitle.text = responseObject.getString("title")
                binding.memeAuthor.text = responseObject.getString("author")
//binding NameImage
                Glide.with(this).load(responseObject.get("url")).into(binding.memeImage)

                progressDialog.dismiss()


            },
            { error->
                progressDialog.dismiss()
                Toast.makeText(this@MainActivity,"${error.localizedMessage}",Toast.LENGTH_SHORT).show()
            })

        queue.add(stringRequest)

    }
}