package com.manoffocus.mfrickandmorty.components.mftabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mficon.MFIconSize
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText

data class MFTabItem(
    var label: String,
    var icon: ImageVector,
    var content: @Composable () -> Unit
)

@Composable
fun MFTabRow(
    modifier: Modifier,
    tabItems: List<MFTabItem>
) {
    var selectedTabIndex = remember { mutableStateOf(0) }
    var scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TabRow(
            modifier = Modifier
                .fillMaxHeight(0.2F)
                .fillMaxWidth(),
            selectedTabIndex = selectedTabIndex.value,
            contentColor = MaterialTheme.colors.primary,
            backgroundColor = Color.Transparent
        ) {
            tabItems.forEachIndexed{ index, item ->
                Tab(
                    modifier = Modifier.fillMaxWidth(),
                    selected = selectedTabIndex.value == index, onClick = { selectedTabIndex.value = index }) {
                    MFTabItem(
                        modifier = Modifier,
                        label = item.label,
                        icon = item.icon
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                tabItems[selectedTabIndex.value].content()
            }
        }
    }
}

@Composable
fun MFTabItem(
    modifier: Modifier,
    label: String,
    icon: ImageVector
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MFIcon(
            modifier = Modifier,
            iconVector = icon,
            contentDescription = label,
            size = MFIconSize.XSMALL
        )
        MFText(
            text = label
        )
    }
}
