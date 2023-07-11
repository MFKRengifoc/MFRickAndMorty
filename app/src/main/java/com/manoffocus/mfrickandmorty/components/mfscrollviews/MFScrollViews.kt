package com.manoffocus.mfrickandmorty.components.mfscrollviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MFHorizontal(
    modifier: Modifier,
    list: List<Any> = emptyList(),
    content : @Composable ((Any) -> Unit)
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(list){ item ->
            content(item)
        }
    }
}
@Composable
fun MFVertical(
    modifier: Modifier,
    list: List<Any> = emptyList(),
    content : @Composable ((Any) -> Unit)
) {
    LazyColumn(
        modifier = modifier.background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list){ item ->
            content(item)
        }
    }
}