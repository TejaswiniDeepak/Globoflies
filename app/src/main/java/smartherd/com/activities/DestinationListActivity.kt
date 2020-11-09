package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globoflies.R

import smartherd.com.helpers.DestinationAdapter
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import smartherd.com.services.DestinationService
import smartherd.com.services.ServiceBuilder


class DestinationListActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		setSupportActionBar(toolbar)
		toolbar.title = title

		fab.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {

        // To be replaced by retrofit code
		//destiny_recycler_view.adapter = DestinationAdapter(SampleData.DESTINATIONS)
		val filter=HashMap<String,String>()
		filter["country"] = "India"
		filter["count"]="1"



		val destinationService=ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall=destinationService.getDestinationList(filter)
		requestCall.enqueue(object :Callback<List<Destination>>
		{
			override fun onResponse(
				call: Call<List<Destination>>,
				response: Response<List<Destination>>
			) {
				if(response.isSuccessful)
				{
					val destinationList=response.body()!!

					destiny_recycler_view.adapter = DestinationAdapter(destinationList)
				}
				else
				{
					Toast.makeText(this@DestinationListActivity,"data couldnt be reterived",
							Toast.LENGTH_SHORT).show()
				}
			}

			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
Log.i("failure","$t")
			}

		})
    }
}
