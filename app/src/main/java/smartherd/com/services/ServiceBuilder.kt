package smartherd.com.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ServiceBuilder {
private const val URL="http://10.0.2.2:9000/"
    //create logger
    val logger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    //custom interceptor to apply Headers application wide
    val headerInterceptor=object:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            var request=chain.request()
            request=request.newBuilder()
                    .addHeader("x-device-type", Build.DEVICE)
                    .addHeader("Accept-Language", Locale.getDefault().language)
                    .build()

            val response=chain.proceed(request)
            return response
        }

    }



    //okHTTP client creation
    private val okHttp=OkHttpClient.Builder().
                    callTimeout(5,TimeUnit.SECONDS).
                    addInterceptor(headerInterceptor).
                    addInterceptor(logger)
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