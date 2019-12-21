package com.example.mykotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mykotlin.R
import com.example.mykotlin.entity.Bean
import com.example.mykotlin.api.MyApi
import com.example.mykotlin.util.RetrofitUtil
import com.example.mykotlin.util.RetrofitUtil.onResponseListener
import com.example.mykotlin.util.RsaCoder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_regist.*
import kotlin.collections.HashMap

class RegistActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)



        regist_btn.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.regist_btn ->{
                var gson : Gson = Gson()

                var pwd = regist_edit_pwd.text.toString()
                var toPwd = RsaCoder.encryptByPublicKey(pwd)

                var hashMap = HashMap<String, String>()
                hashMap.put("nickName",regist_edit_name.text.toString())
                hashMap.put("phone",regist_edit_phone.text.toString())
                hashMap.put("pwd",toPwd)

                RetrofitUtil.instance.doPost(MyApi.url_regist,hashMap, object : onResponseListener{
                    override fun onSuccess(string: String) {
                        var tojson = gson.fromJson(string,Bean::class.java)

                        Toast.makeText(this@RegistActivity,tojson.message,Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(string: String) {
                        Toast.makeText(this@RegistActivity,string,Toast.LENGTH_SHORT).show()
                    }
                })


            }
        }

    }
}
