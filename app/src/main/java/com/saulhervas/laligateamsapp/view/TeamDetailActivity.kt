package com.saulhervas.laligateamsapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.saulhervas.laligateamsapp.R
import com.saulhervas.laligateamsapp.databinding.ActivityTeamDetailBinding
import com.saulhervas.laligateamsapp.utils.ApiService
import com.saulhervas.laligateamsapp.utils.TeamsDataResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeamDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityTeamDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getIntExtra(EXTRA_ID, 0)
        getTeamInfo(id)

    }

    private fun getTeamInfo(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val teamDetail = getRetrofit().create(ApiService::class.java).getTeam(id)

            if (teamDetail.body() != null) {
                runOnUiThread { createUI(teamDetail.body()!!) }
            }
        }
    }

    private fun createUI(team: TeamsDataResponse) {
        Picasso.get().load(team.responseTeam[0].team.logo).into(binding.ivImage)
        binding.tvTeamName.text = team.responseTeam[0].team.name
        binding.tvteamNameDetail.text = team.responseTeam[0].team.name
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