package com.manoffocus.mfrickandmorty.components.mfsection

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MFSectionForHorizontal(
    modifier: Modifier,
    scrollState : ScrollState? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    content: @Composable() () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        scrollState?.let { scroll ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scroll),
                horizontalArrangement = horizontalArrangement
            ) {
                content()
            }
        } ?: run {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = horizontalArrangement
            ) {
                content()
            }
        }
    }
}