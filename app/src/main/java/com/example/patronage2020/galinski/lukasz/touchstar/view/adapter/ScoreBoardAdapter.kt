package com.example.patronage2020.galinski.lukasz.touchstar.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.patronage2020.galinski.lukasz.touchstar.R
import com.example.patronage2020.galinski.lukasz.touchstar.model.ScoreModel
import kotlinx.android.synthetic.main.score_board_row.view.*

class ScoreBoardAdapter(private val context: Context): RecyclerView.Adapter<ScoreBoardAdapter.ScoreViewHolder>(){

    var score: List<ScoreModel> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(LayoutInflater.from(context).inflate(R.layout.score_board_row, parent, false))
    }

    override fun getItemCount(): Int {
        return score.size
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.id.text = (position+1).toString()
        holder.name.text = score[position].userName
        holder.score.text = score[position].score.toString()
        holder.stage.text = score[position].stage.toString()
    }

    class ScoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.score_id
        val name: TextView = itemView.score_name
        val score: TextView = itemView.score_score
        val stage: TextView = itemView.score_stage
    }
}