package com.example.weatherapplication.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * @author Kalmykova Natalia
 */
abstract class BaseViewModel : ViewModel() {

    private var isStarted: Boolean = false

    fun start() {
        if (!isStarted) {
            isStarted = true
            onStart()
        }
    }

    protected abstract fun onStart()

    protected val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    protected val _errorLiveData = SingleEventLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    fun <P> doWorkInMainThread(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, Main)
    }

    fun <P> doWork(doOnAsyncBlock: suspend CoroutineScope.() -> P) {
        doCoroutineWork(doOnAsyncBlock, viewModelScope, IO)
    }

    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        coroutineScope: CoroutineScope,
        context: CoroutineContext
    ) {
        coroutineScope.launch {
            withContext(context) {
                doOnAsyncBlock.invoke(this)
            }
        }
    }

    protected inline fun handleLoading(errorMessage: String, function: () -> Unit) {
        _loadingLiveData.postValue(true)
        try {
            function()
        } catch (e: Exception) {
            e.printStackTrace()
            _errorLiveData.postValue(errorMessage)
        }
        _loadingLiveData.postValue(false)
    }
}