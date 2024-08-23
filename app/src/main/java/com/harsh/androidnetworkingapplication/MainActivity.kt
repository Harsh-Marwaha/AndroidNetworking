package com.harsh.androidnetworkingapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    var recViewAdapter: RecViewAdapter? = null
    var btnSignUp : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp?.setOnClickListener(){
            val intent : Intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        var     recView : RecyclerView = findViewById(R.id.recView)

        var newsResponse:NewsResponse? = null
        var arrNewsArticle:ArrayList<NewsArticlesModel> = ArrayList()

        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.get("https://newsapi.org/v2/everything")
            .addQueryParameter("q","tesla")
            .addQueryParameter("from","publishedAt")
            .addQueryParameter("sortBy","2024-06-29")
            .addQueryParameter("apiKey","83f340fcb6dd4d4fa3f1a9eba155e586")
            .setTag("test")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    //Log.d(TAG, "onResponse: ${response.toString()}")

                   // Log.d(TAG, "onResponse: Status: ${response?.getString("status")}")
                    Log.d(TAG, "onResponse: Status: ${response?.getJSONArray("articles")}")


                    val jsonArray = response?.getJSONArray("articles")
                    if(jsonArray != null) {
                        for (i in 0 until jsonArray.length()) {
                            val art:JSONObject = jsonArray[i] as JSONObject
                           // Log.d(TAG, "onResponse: art: ${art}")
                            Log.d(TAG, "onResponse: Source: ${art.getJSONObject("source")}")
                            val model = NewsArticlesModel(author = art.getString("author"), title = art.getString("title"), description = art.getString("description"), url = art.getString("url"), urlToImage = art.getString("urlToImage"), publishedAt = art.getString("publishedAt"), content = art.getString("content"))
                            arrNewsArticle.add(model)
                        }
                    }
                    newsResponse =NewsResponse(arrNewsArticle);
                    Log.d(TAG, "onResponse: arr : $newsResponse")
                }

                override fun onError(anError: ANError?) {
                    Log.d(TAG, "onError: ${anError?.errorBody}")
                }

            })

        recView.layoutManager = LinearLayoutManager(this@MainActivity)
        recViewAdapter = RecViewAdapter(this@MainActivity,arrNewsArticle)
        recViewAdapter!!.notifyDataSetChanged()
        recView.adapter = recViewAdapter


    }
}