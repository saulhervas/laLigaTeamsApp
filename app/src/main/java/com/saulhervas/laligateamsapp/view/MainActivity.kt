package com.saulhervas.laligateamsapp.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saulhervas.laligateamsapp.R
import com.saulhervas.laligateamsapp.databinding.ActivityMainBinding
import com.saulhervas.laligateamsapp.utils.ApiService
import com.saulhervas.laligateamsapp.utils.StandingsDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        retrofit = getRetrofit()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        works()
    }

    private fun works() {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<StandingsDataResponse> =
                retrofit.create(ApiService::class.java).getStandings()
            Log.d("TAG", "funciona")
            val response: StandingsDataResponse? = myResponse.body()
            Log.i("TAG", response.toString())
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://v3.football.api-sports.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .addHeader("X-RapidAPI-Key", "98fe0d509274182bfb18ff7b5e8f6d15")
                            .addHeader("X-RapidAPI-Host", "v3.football.api-sports.io")
                            .build()
                        chain.proceed(newRequest)
                    }
                    .build()
            )
            .build()
    }
}