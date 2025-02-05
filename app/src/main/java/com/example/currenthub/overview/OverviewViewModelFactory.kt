package com.example.currenthub.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OverviewViewModelFactory () : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}