package com.saulhervas.laligateamsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saulhervas.laligateamsapp.R
import com.saulhervas.laligateamsapp.utils.Response
import com.saulhervas.laligateamsapp.viewHolder.TeamViewHolder

class TeamsAdapter(
    var teamsList: List<Response> = emptyList(),
    private val onItemSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<TeamViewHolder>() {

    fun updateList(teamsList: List<Response>) {
        this.teamsList = teamsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TeamViewHolder(layoutInflater.inflate(R.layout.item_teams, parent, false))
    }

    override fun onBindViewHolder(viewHolder: TeamViewHolder, position: Int) {
        viewHolder.bind(teamsList[position], onItemSelected)
    }

    override fun getItemCount(): Int = teamsList.size
}