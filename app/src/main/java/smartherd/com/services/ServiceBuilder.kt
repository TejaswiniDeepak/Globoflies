package smartherd.com.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
private const val URL="http://10.0.2.2:9000/"
    val logger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    //okHTTP client creation
    private val okHttp=OkHttpClient.Builder().addInterceptor(logger)
//retrofit creation
    private val builder=Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())
//instance of retrofit
    private val retrofit= builder.build()
// function that accepts Generic Class
    fun <T>buildService(serviceType:Class<T>):T{
        return  retrofit.create(serviceType)
    }
}