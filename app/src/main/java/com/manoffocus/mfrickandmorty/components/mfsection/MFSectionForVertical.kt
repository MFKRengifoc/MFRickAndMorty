package com.manoffocus.mfrickandmorty.components.mfsection

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MFSectionForVertical(
    modifier: Modifier,
    scrollState : ScrollState? = null,
    verticalAlignmentR: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangementR: Arrangement.Horizontal = Arrangement.Center,
    verticalArrangementC: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignmentC: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable() () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignmentR,
        horizontalArrangement = horizontalArrangementR
    ) {
        scrollState?.let { scroll ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scroll),
                verticalArrangement = verticalArrangementC,
                horizontalAlignment = horizontalAlignmentC
            ) {
                content()
            }
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = verticalArrangementC,
                horizontalAlignment = horizontalAlignmentC
            ) {
                content()
            }
        }
    }
}