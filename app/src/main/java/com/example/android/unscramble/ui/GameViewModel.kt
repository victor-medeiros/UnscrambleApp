package com.example.android.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }

    private fun shuffleCurretnWord(word: String): String {
        val tempWord = word.toCharArray()

        while (tempWord.toString() == word) {
            tempWord.shuffle()
        }
        return tempWord.toString()
    }

    private fun pickRandomWordAndShuffle(): String {
        val currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            shuffleCurretnWord(currentWord)
        }
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }
}