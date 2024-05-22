package com.saulhervas.laligateamsapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.saulhervas.laligateamsapp.adapter.TeamsAdapter
import com.saulhervas.laligateamsapp.databinding.ActivityMainBinding
import com.saulhervas.laligateamsapp.utils.ApiClient
import com.saulhervas.laligateamsapp.utils.ApiService
import com.saulhervas.laligateamsapp.utils.TeamsDataResponse
import com.saulhervas.laligateamsapp.view.TeamDetailActivity.Companion.EXTRA_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: TeamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        retrofit = ApiClient.getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.swTeams.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = TeamsAdapter { navigateToTeamDetail(it) }
        binding.rvTeams.setHasFixedSize(true)
        binding.rvTeams.layoutManager = LinearLayoutManager(this)
        binding.rvTeams.adapter = adapter
        binding.bntClean.setOnClickListener { adapter.clearList() }
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<TeamsDataResponse> =
                retrofit.create(ApiService::class.java).getTeams(query)
            if (myResponse.isSuccessful) {
                Log.d("TAG", "Funciona")
                val response: TeamsDataResponse? = myResponse.body()
                if (response != null) {
                    Log.d("TAG", response.responseTeam.toString())
                    runOnUiThread {
                        adapter.updateList(response.responseTeam)
                        binding.progressBar.isVisible = false
                    }
                }
            } else {
                Log.d("TAG", "No funciona")
            }
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }



    private fun navigateToTeamDetail(id: Int) {
        val intent = Intent(this, TeamDetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}

