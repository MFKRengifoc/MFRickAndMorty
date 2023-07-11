package com.manoffocus.mfrickandmorty.components.mfsearchfilterbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.ui.theme.mfSearchScreenDeadColor
import com.manoffocus.mfrickandmorty.ui.theme.mfSearchScreenFemaleColor
import com.manoffocus.mfrickandmorty.ui.theme.mfSearchScreenMaleColor

@Composable
fun MFSearchFilterBar(
    modifier: Modifier,
    onFilter: (status: String, gender: String) -> Unit
) {
    val statusFilter = remember { mutableStateOf("") }
    val genderFilter = remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 5.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ){
        MFFilterPicker(
            modifier = Modifier.width(IntrinsicSize.Max),
            title = stringResource(id = R.string.mf_search_screen_title_status_filter_label),
            options = arrayOf(
                MFFilterOption(
                    value = stringResource(id = R.string.mf_search_screen_alive_filter_label),
                    color = Color.Green
                ),
                MFFilterOption(
                    value = stringResource(id = R.string.mf_search_screen_dead_filter_label),
                    color = mfSearchScreenDeadColor
                )
            )
        ){
            statusFilter.value = it
            onFilter.invoke(statusFilter.value, genderFilter.value)
        }
        MFFilterPicker(
            modifier = Modifier.width(IntrinsicSize.Max),
            title = stringResource(id = R.string.mf_search_screen_title_gender_filter_label),
            options = arrayOf(
                MFFilterOption(
                    value = stringResource(id = R.string.mf_search_screen_male_filter_label),
                    color = mfSearchScreenMaleColor
                ),
                MFFilterOption(
                    value = stringResource(id = R.string.mf_search_screen_female_filter_label),
                    color = mfSearchScreenFemaleColor
                ),
                MFFilterOption(
                    value = stringResource(id = R.string.mf_search_screen_unknown_filter_label),
                    color = MaterialTheme.colors.primary
                )
            )
        ){
            genderFilter.value = it
            onFilter.invoke(statusFilter.value, genderFilter.value)
        }
    }
}