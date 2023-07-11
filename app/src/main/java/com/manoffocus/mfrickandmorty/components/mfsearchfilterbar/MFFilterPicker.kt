package com.manoffocus.mfrickandmorty.components.mfsearchfilterbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChip
import com.manoffocus.mfrickandmorty.components.mfchipicon.MFChipSize
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.ui.theme.mfTextVPadding

data class MFFilterOption(
    var value: String,
    var color: Color
)

@Composable
fun MFFilterPicker(
    modifier: Modifier,
    title: String,
    options: Array<MFFilterOption>,
    onItemClick: (String) -> Unit
) {
    val selected = remember { mutableStateOf(-1) }
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            MFText(
                modifier = Modifier.padding(
                    vertical = mfTextVPadding
                ),
                text = title,
                size = MFTexSizes.XSMALL,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 5.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                for (index in options.indices){
                    MFChip(
                        label = options[index].value,
                        size = MFChipSize.XSMALL,
                        color = if (selected.value != index) options[index].color.copy(alpha = 0.5F, blue = 0.4F) else options[index].color
                    ){
                        if (selected.value == index){
                            selected.value = -1
                            onItemClick.invoke("")
                        } else {
                            selected.value = index
                            onItemClick.invoke(options[index].value)
                        }
                    }
                }
            }
        }
    }
}