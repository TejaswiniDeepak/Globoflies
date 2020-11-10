package com.smartherd.globofly.activities

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globoflies.R

import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import smartherd.com.services.DestinationService
import smartherd.com.services.ServiceBuilder


class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			// To be replaced by retrofit code
			val serviceBuilder=ServiceBuilder.buildService(DestinationService::class.java) // Move back to DestinationListActivity
			val callRequest=serviceBuilder.addDestination(newDestination)
			callRequest.enqueue(object : Callback<Destination> {
				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if(response.isSuccessful) {
						Toast.makeText(this@DestinationCreateActivity, "Resource added", Toast.LENGTH_SHORT).show()
						finish()
					}
					else
					{
						Toast.makeText(this@DestinationCreateActivity, "Resource cannot be added",
								Toast.LENGTH_SHORT).show()
					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationCreateActivity, "Failure in Resource adding", Toast.LENGTH_SHORT).show()
				}

			})
		}
	}
}
