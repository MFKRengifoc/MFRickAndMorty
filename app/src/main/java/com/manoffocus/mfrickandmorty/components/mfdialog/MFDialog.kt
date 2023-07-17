package com.manoffocus.mfrickandmorty.components.mfdialog

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle

@Composable
fun MFDialog(
    title: String,
    show: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    if (show.value){
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                MFTextTitle(text = title)
            },
            text = {
                content()
            },
            confirmButton = {

            }
        )
    }
}