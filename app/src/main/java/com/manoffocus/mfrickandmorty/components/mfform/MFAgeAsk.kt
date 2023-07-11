package com.manoffocus.mfrickandmorty.components.mfform

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mficon.MFIconSize
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

@Composable
fun MFAgeAsk(
    modifier: Modifier = Modifier,
    onValidAge: (Boolean, Int) -> Unit
) {
    // Rated in 14 by Pluggedin.com
    val minAge = 15
    val maxAge = 89
    val ageState = remember { mutableStateOf(14) }
    val validValue =  remember(ageState.value) {
        ageState.value >= minAge
    }
    Column(
        modifier = modifier
            .padding(vertical = verticalPaddingBg, horizontal = horizontalPaddingBg),

    ){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            MFText(
                modifier = modifier.width(100.dp),
                text = ageState.value.toString(),
                size = MFTexSizes.LARGE
            )
            Spacer(modifier = Modifier.width(10.dp))
            MFAgeChanger(age = ageState){ age ->
                onValidAge.invoke(validValue, ageState.value)
            }
        }
        Row(modifier = modifier) {
            val text = getAgeMessage(LocalContext.current, ageState.value)
            MFText(
                text = text,
                color = MaterialTheme.colors.error,
                size = MFTexSizes.XSMALL
            )
        }
    }
}

private fun getAgeMessage(ctx: Context, age: Int): String{
    return when {
        age == 0 -> ctx.getString(R.string.mf_profiler_screen_dont_exist)
        age < 9 -> ctx.getString(R.string.mf_profiler_screen_so_baby_label)
        age < 14 -> ctx.getString(R.string.mf_profiler_screen_min_age_label)
        age > 110 -> ctx.getString(R.string.mf_profiler_screen_so_old)
        age > 89 -> ctx.getString(R.string.mf_profiler_screen_max_age_label)
        age > 50 -> ctx.getString(R.string.mf_profiler_screen_try_another)
        age > 25 -> ctx.getString(R.string.mf_profiler_screen_free_time)
        else -> ""
    }
}

@Composable
private fun MFAgeChanger(
    modifier: Modifier = Modifier,
    age: MutableState<Int>,
    onAgeChange: (Int) -> Unit
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 5.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MFIcon(
            modifier = Modifier,
            contentDescription = stringResource(id = R.string.mf_cd_add_icon),
            image = R.drawable.mf_profilerscreen_add_icon,
            size = MFIconSize.SMALL
        ){
            age.value++
            onAgeChange.invoke(age.value)
        }
        MFIcon(
            modifier = Modifier,
            contentDescription = stringResource(id = R.string.mf_cd_quit_icon),
            image = R.drawable.mf_profilerscreen_quit_icon,
            size = MFIconSize.SMALL
        ){
            age.value--
            if (age.value < 0) age.value = 0
            onAgeChange.invoke(age.value)
        }
    }
}