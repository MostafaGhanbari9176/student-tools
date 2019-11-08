package ir.pepotec.app.awesomeapp.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import ir.pepotec.app.awesomeapp.view.uses.App
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ApiClient {

    companion object {
        val serverAddress = "http://pepotec.ir/new_app_api/v1/"
        var retrofit: Retrofit? = null
        var okhttp: OkHttpClient? = null

        fun getClient(): Retrofit {
            if (retrofit == null) {
                okhttp = getokHttpClient()
                retrofit = Retrofit.Builder()
                    .baseUrl(serverAddress)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getokHttpClient())
                    .build()
            }
            return retrofit!!
        }

        private fun getokHttpClient(): OkHttpClient {
            val cacheSize = (5 * 1024 * 1024).toLong()
            val dir = File(App.instanse.cacheDir, "retrofit")
            val myCache = Cache(dir, cacheSize)
            return OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(object:Interceptor{
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val orgResponse = chain.proceed(chain.request())
                        if(hasNetwork(App.instanse) == true)
                        {
                            val maxAge = 60 // one minute caching
                            return orgResponse.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).build()
                        }
                        val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                        return orgResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                            .build()
                    }
                })
                .build()
        }
        private fun hasNetwork(context: Context): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }


}