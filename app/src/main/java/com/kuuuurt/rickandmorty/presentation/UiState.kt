package com.kuuuurt.rickandmorty.presentation

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 10/08/2020
 */

sealed class UiState {
    class Loading() : UiState()
    class Complete() : UiState()
    class Error(private val throwable: Throwable) : UiState()
}