package com.example.work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.work.calllist.call_log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //获取短信 按钮
        getduanxin.setOnClickListener {
            val intent = Intent(this,duanxin_test::class.java)
            startActivity(intent)

        }

        //获取通讯录
        gettongxunlu.setOnClickListener {
            val intent = Intent(this,tongxunlu::class.java)
            startActivity(intent)

        }

        //获取gps
        getgps.setOnClickListener {
            val intent = Intent(this,gps::class.java)
            startActivity(intent)

        }

        //获取wifi
        getwifi_bt.setOnClickListener {
            val intent = Intent(this,getwifi::class.java)
            startActivity(intent)
        }

        //获取应用列表
        getyingyong.setOnClickListener {
            val intent = Intent(this, com.example.work.applist.application::class.java)
            startActivity(intent)

        }

        //获取通话记录
        gettonghuajilu.setOnClickListener {
            val intent = Intent(this, call_log::class.java)
            startActivity(intent)

        }


    }
}