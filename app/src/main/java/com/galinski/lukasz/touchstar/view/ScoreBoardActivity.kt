package com.galinski.lukasz.touchstar.view

import android.os.Bundle
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
        val scoreListObservable = Observable.fromCallable { scoreBoardViewModel.init(this) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { scoreBoardViewModel.getScoreList()?.value!! }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                scoreAdapter.score = it
            }
        compositeDisposable.add(scoreListObservable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
