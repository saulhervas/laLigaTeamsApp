package com.saulhervas.laligateamsapp.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.saulhervas.laligateamsapp.databinding.ItemTeamsBinding
import com.saulhervas.laligateamsapp.utils.Response


import com.squareup.picasso.Picasso

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTeamsBinding.bind(view)
    fun bind(team: Response, onItemSelected: (Int) -> Unit) {
        binding.tvTeams.text = team.team?.name
        binding.ivIcon
        Picasso.get().load(team.team?.logo).into(binding.ivIcon)
        binding.root.setOnClickListener { team.team?.id?.let { it1 -> onItemSelected(it1) } }
    }
}


