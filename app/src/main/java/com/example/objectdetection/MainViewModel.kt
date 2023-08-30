package com.example.objectdetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.objectdetection.data.Insect
import com.example.objectdetection.data.supportedInsects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    var query = _query.asStateFlow();


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow();


    private val _insects = MutableStateFlow(supportedInsects)
    val insects = query
        .onEach { _isSearching.update { true } }
        .combine(_insects) { q, ins ->
            if (q.isNullOrEmpty()) {
                ins
            } else {
                ins.filter { insect -> insect.matchQuery(q) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _insects.value)

    fun onSearchTextChange(text: String) {
        _query.value = text
    }

}