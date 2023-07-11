package com.manoffocus.mfrickandmorty.components.mfdialog

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle

@Composable
fun MFDialog(
    title: String,
    content: @Composable () -> Unit
) {
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