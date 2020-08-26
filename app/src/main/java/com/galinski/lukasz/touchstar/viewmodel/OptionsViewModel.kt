package com.galinski.lukasz.touchstar.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.galinski.lukasz.touchstar.repository.SharedPreferencesRepository

class OptionsViewModel: ViewModel() {
    private var sharedRepo: SharedPreferencesRepository? = null

    fun instance(){
        if (sharedRepo != null){
            return
        }
        sharedRepo = SharedPreferencesRepository().instance()
    }

    fun changeDefaultStarSymbol(context: Context, currentSign: String){
        sharedRepo?.changeDefaultStarSymbol(context, currentSign)
    }

}