 package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.telecom.Call

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globoflies.R

import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Response
import smartherd.com.services.MessageService
import smartherd.com.services.ServiceBuilder
import javax.security.auth.callback.Callback


 class WelcomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		// To be replaced by retrofit code
		//message.text = "Black Friday! Get 50% cash back on saving your first spot."
		val buildService=ServiceBuilder.buildService(MessageService::class.java)
		val requestCall=buildService.getMessages("http://10.0.2.2:7000/messages")
		requestCall.enqueue(object :retrofit2.Callback<String> {
			override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
				if(response.isSuccessful)
				{
					val msg=response.body()
					message.text=msg
				}
				else
				{
					Toast.makeText(this@WelcomeActivity,"DATA cant be reterived",Toast.LENGTH_SHORT).show()
				}
			}

			override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,"onFailure DATA cant be reterived",Toast.LENGTH_SHORT).show()

			}

		})
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
