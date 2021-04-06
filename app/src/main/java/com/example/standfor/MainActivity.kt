package com.example.standfor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.standfor.adapter.AcronymAdapter
import com.example.standfor.network.EndPoint
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val BASE_URL = "http://www.nactem.ac.uk/software/acromine/"

        val endPoint: EndPoint = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build().create(EndPoint::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.m_recycler)
        val layoutManager = LinearLayoutManager(this)
        val editText: EditText = findViewById(R.id.m_edit)
        recyclerView.layoutManager = layoutManager

        val fab: FloatingActionButton = findViewById(R.id.fab_acronyms)
        fab.setOnClickListener {

            if (!editText.text.isEmpty()) {
                endPoint.getAcronyms(editText.text.toString()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({
                            val adapter = AcronymAdapter(it.get(0).lfl)
                            recyclerView.adapter = adapter
                            Log.i(TAG, it.get(0).lfl.size.toString())
                        }, {
                            Log.i(TAG, "ERROR ACRONYMUN " + it.message.toString())
                            if (it.message.equals("Index: 0, Size: 0")) {
                                Toast.makeText(this, "No Acronyms found", Toast.LENGTH_SHORT).show()

                            } else {
                                Toast.makeText(this, "Something went wrogn, please try again", Toast.LENGTH_SHORT).show()
                            }
                        })
            } else {
                Toast.makeText(this, "Write something!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}