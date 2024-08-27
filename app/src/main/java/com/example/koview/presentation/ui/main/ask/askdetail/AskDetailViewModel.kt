package com.example.koview.presentation.ui.main.ask.askdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koview.data.model.BaseState
import com.example.koview.data.model.response.QueryAnswerList
import com.example.koview.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AskDetailEvent {
    data object NavigateToAsk : AskDetailEvent()
    data object NavigateToAskAnswer : AskDetailEvent()
    data class ShowToastMessage(val msg: String) : AskDetailEvent()
}

@HiltViewModel
class AskDetailViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _event = MutableSharedFlow<AskDetailEvent>()
    val event: SharedFlow<AskDetailEvent> = _event.asSharedFlow()

    private val _getAnswers = MutableStateFlow<List<QueryAnswerList>>(emptyList())
    val getAnswers: StateFlow<List<QueryAnswerList>> = _getAnswers.asStateFlow()

    fun navigateToAsk() {
        viewModelScope.launch {
            _event.emit(AskDetailEvent.NavigateToAsk)
        }
    }

    fun navigateToAskAnswer() {
        viewModelScope.launch {
            _event.emit(AskDetailEvent.NavigateToAskAnswer)
        }
    }

    fun getQueryAnswers(queryId: Long) {
        viewModelScope.launch {
            repository.getQueryAnswers(queryId).let {
                when (it) {
                    is BaseState.Error -> {
                        _event.emit(AskDetailEvent.ShowToastMessage(it.msg))
                    }

                    is BaseState.Success -> {
                        _getAnswers.value = it.body.result.answerList
                    }
                }
            }
        }
    }
}