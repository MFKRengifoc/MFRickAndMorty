package com.manoffocus.mfrickandmorty.components.mflottie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.manoffocus.mfrickandmorty.R

@Preview
@Composable
fun MFIconLoader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = R.drawable.mf_loading_avatar_icon),
            contentDescription = stringResource(id = R.string.mf_topbar_icon_loading_label) )
    }
}