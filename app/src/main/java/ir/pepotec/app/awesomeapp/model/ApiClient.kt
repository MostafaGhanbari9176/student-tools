package ir.pepotec.app.awesomeapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        val serverAddress = "http://192.168.1.200:8080/"
        val baseUrl = "GreatAppApi/v1/index.php/"
        var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(serverAddress + baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }

}