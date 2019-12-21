package com.example.mykotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mykotlin.R
import com.example.mykotlin.api.MyApi
import com.example.mykotlin.entity.Bean
import com.example.mykotlin.util.RetrofitUtil
import com.example.mykotlin.util.RsaCoder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        login_tv_rg.setOnClickListener(this)
        login_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.login_tv_rg -> {
                startActivity(Intent(this,RegistActivity::class.java))
            }
            R.id.login_btn -> {
                var gson : Gson = Gson()

                var pwd = login_edit_pwd.text.toString()
                var toPwd = RsaCoder.encryptByPublicKey(pwd)

                var hashMap = HashMap<String,String>()
                hashMap.put("phone",login_edit_phone.text.toString())
                hashMap.put("pwd",toPwd)

                RetrofitUtil.instance.doPost(MyApi.url_login,hashMap,object : RetrofitUtil.onResponseListener{
                    override fun onSuccess(string: String) {
                        var tojson = gson.fromJson(string, Bean::class.java)

                        Toast.makeText(this@LoginActivity,tojson.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFail(string: String) {
                        Toast.makeText(this@LoginActivity,string, Toast.LENGTH_SHORT).show()
                    }

                })

            }
        }
    }
}
