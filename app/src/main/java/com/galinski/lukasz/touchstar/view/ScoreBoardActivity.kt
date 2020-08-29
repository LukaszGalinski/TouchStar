package com.galinski.lukasz.touchstar.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.galinski.lukasz.touchstar.R
import com.galinski.lukasz.touchstar.view.adapter.ScoreBoardAdapter
import com.galinski.lukasz.touchstar.viewmodel.ScoreBoardViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.score_board_layout.*

class ScoreBoardActivity : AppCompatActivity() {
    private lateinit var scoreBoardViewModel: ScoreBoardViewModel
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var scoreAdapter: ScoreBoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_board_layout)
        compositeDisposable = CompositeDisposable()
        scoreBoardViewModel = ViewModelProvider(this).get(ScoreBoardViewModel::class.java)
        loadScoreList()
        buildRecyclerView()
    }

    private fun buildRecyclerView() {
        scoreAdapter = ScoreBoardAdapter(this)
        score_list.layoutManager = LinearLayoutManager(this)
        score_list.adapter = scoreAdapter
    }

    private fun loadScoreList() {
        showProgressBar()
        showLoadingMessage()
        val scoreListObservable = Observable.fromCallable { scoreBoardViewModel.init(this) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { scoreBoardViewModel.getScoreList()?.value!! }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete{
                hideProgressBar()
                showLoadingDoneMessage()
            }
            .subscribe {
                scoreAdapter.score = it
            }
        compositeDisposable.add(scoreListObservable)
    }

    private fun showProgressBar(){
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progress_bar.visibility = View.GONE
    }

    private fun showLoadingMessage(){
        Toast.makeText(this, resources.getString(R.string.data_loading), Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingDoneMessage(){
        Toast.makeText(this, resources.getString(R.string.data_loading_done), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
