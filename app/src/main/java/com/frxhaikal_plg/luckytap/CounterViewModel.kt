package com.frxhaikal_plg.luckytap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CounterViewModel : ViewModel() {
    private val _counter = MutableLiveData(0)
    private val _status = MutableLiveData("Status: Genap")
    private val _jackpotProbability = MutableLiveData(0.01)

    val counter: LiveData<Int> get() = _counter
    val status: LiveData<String> get() = _status
    val jackpotProbability: LiveData<Double> get() = _jackpotProbability

    fun increment() {
        val currentCounter = (_counter.value ?: 0) + 1
        _counter.value = currentCounter

        _status.value = "Status: ${if (currentCounter % 2 == 0) "Genap" else "Ganjil"}"

        val currentProbability = _jackpotProbability.value ?: 0.01
        if (currentCounter % 2 != 0 && currentCounter > 10 && Random.nextDouble(0.0, 1.0) < currentProbability) {
            _jackpotProbability.value = -1.0
        } else {
            _jackpotProbability.value = (currentProbability + 0.01).coerceAtMost(0.05)
        }
    }

    fun reset() {
        _counter.value = 0
        _status.value = "Status: Genap"
        _jackpotProbability.value = 0.01
    }
}
