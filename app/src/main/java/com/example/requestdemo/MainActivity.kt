package com.example.requestdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.requestdemo.api.DemoApiService
import com.example.requestdemo.util.getService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRequest.setOnClickListener {
            tvResult.setText("等待请求结果")
            DemoApiService::class.java.getService().demoGet().subscribe({
                    val text = Gson().toJson(it)
                    tvResult.setText(text)
                },{
                    val text = Log.getStackTraceString(it)
                    tvResult.setText(text)
                })
        }
    }
}
