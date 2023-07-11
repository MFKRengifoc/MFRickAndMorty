package com.manoffocus.mfrickandmorty.components.mfform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mficon.MFIconSize
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.ui.theme.mfInputBottomColor
import com.manoffocus.mfrickandmorty.ui.theme.mfInputErrorColor
import com.manoffocus.mfrickandmorty.ui.theme.mfInputFocusedLabelColor
import com.manoffocus.mfrickandmorty.ui.theme.mfInputTextColor
import com.manoffocus.mfrickandmorty.ui.theme.mfInputTextSize
import com.manoffocus.mfrickandmorty.ui.theme.mfInputUnFocusedLabelColor
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg


@Composable
fun MFInput(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    label: String,
    type: KeyboardType = KeyboardType.Text,
    onValueChange: (new: String, previous: String) -> Unit,
    imeAction: ImeAction,
    showTrailingIcon: Boolean,
    validFilters: List<Map<String, String>>? = null,
    onCompleted: (String?) -> Unit
){
    var hasFocus by remember {
        mutableStateOf(false)
    }
    val validValue =  remember(valueState.value) {
        valueState.value.trim().isNotEmpty() && validFilters.isNullOrEmpty()
    }
    Row(
        modifier = modifier
            .padding(vertical = topBottomPaddingBg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .onFocusEvent { item ->
                        hasFocus = item.hasFocus
                    }
                    .background(
                        color = Color.Transparent
                    )
                ,
                value = valueState.value,
                onValueChange = { new ->
                    onValueChange(new, valueState.value)
                    valueState.value = new
                },
                label = {
                    MFText(
                        text = label,
                        size = MFTexSizes.XSMALL,
                        color = mfInputTextColor
                    )
                },
                trailingIcon = {
                    if (showTrailingIcon){
                        if (validValue){
                            MFIcon(
                                modifier = Modifier,
                                contentDescription = stringResource(id = R.string.mf_profiler_screen_cd_input_label, label),
                                iconVector = Icons.Default.CheckCircle,
                                tint = mfInputBottomColor,
                                size = MFIconSize.XSMALL
                            )
                        }
                        if (valueState.value.trim().isNotEmpty() && !validFilters.isNullOrEmpty()){
                            MFIcon(
                                modifier = Modifier,
                                contentDescription = stringResource(id = R.string.mf_profiler_screen_cd_input_label, label),
                                iconVector = Icons.Default.Clear,
                                tint = mfInputErrorColor,
                                size = MFIconSize.XSMALL
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = type,
                    imeAction = imeAction
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedLabelColor = mfInputFocusedLabelColor.copy(alpha = 0.9F),
                    unfocusedLabelColor = mfInputUnFocusedLabelColor.copy(alpha = 0.5F),
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = mfInputBottomColor,
                    unfocusedIndicatorColor = mfInputBottomColor.copy(alpha = 0.5F),
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (validValue){
                            onCompleted(valueState.value)
                        } else {
                            onCompleted(null)
                        }
                    },
                    onNext = {
                        if (validValue){
                            onCompleted(valueState.value)
                        } else {
                            onCompleted(null)
                        }
                    }
                ),
                textStyle = TextStyle(
                    color = mfInputTextColor,
                    fontSize = mfInputTextSize
                )
            )
        }

    }
}