package com.manoffocus.mfrickandmorty.components.mfsnackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MFSnackbar(
    modifier: Modifier = Modifier,
    msg: String
) {
    if (msg == "") return
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarVisibleState = remember { mutableStateOf(false) }

    LaunchedEffect(snackbarVisibleState.value) {
        if (snackbarVisibleState.value) {
            snackbarHostState.showSnackbar(msg)
        }
    }
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier.padding(16.dp)
    ) { snackbarData ->
        Snackbar(
            snackbarData,
            modifier = modifier.padding(8.dp),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        )
    }
    snackbarVisibleState.value = true

}