package com.manoffocus.mfrickandmorty.components.mfcharactersguard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mficon.MFIcon
import com.manoffocus.mfrickandmorty.components.mficon.MFIconSize
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexTitleSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTextTitle
import com.manoffocus.mfrickandmorty.ui.theme.topBottomPaddingBg

enum class MFCharacterTextPosition{
    LEFT,
    RIGHT;
}
enum class MFCharacterMsgSize(var size: Dp){
    BIG(205.dp),
    MEDIUM(190.dp),
    SMALL(80.dp);
}

@Preview(showBackground = false)
@Composable
fun MFCharacterGuard(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.mf_profiler_screen_fun_guard_label),
    msg: MutableState<String> = mutableStateOf("HI"),
    textPosition: MFCharacterTextPosition = MFCharacterTextPosition.RIGHT,
    dialogSize: MFCharacterMsgSize = MFCharacterMsgSize.SMALL,
    icon: Int = R.drawable.mf_rick_icon,
    contentDescription: String = "",
    onClick: () -> Unit = {}
) {
    val direction = getDirection(textPosition)
    val percent = when(dialogSize){
        MFCharacterMsgSize.SMALL -> 30
        MFCharacterMsgSize.MEDIUM -> 25
        MFCharacterMsgSize.BIG -> 10
    }
    val offset = (dialogSize.size * percent)/100

    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        Row(
            modifier = modifier.height(dialogSize.size),
        ) {
            Column(
                modifier = modifier
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                MFIcon(
                    modifier = Modifier,
                    image = icon,
                    contentDescription = contentDescription,
                    elevation = 10.dp,
                    size = MFIconSize.LARGE,
                    backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.1F)
                ){
                    onClick.invoke()
                }
                MFTextTitle(
                    text = title,
                    size = MFTexTitleSizes.SMALL
                )
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Column(
                    modifier = modifier
                        .absoluteOffset(y = -offset)
                ) {
                    Box(
                        modifier = modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .size(dialogSize.size)
                                .padding(all = topBottomPaddingBg)
                                .scale(
                                    scaleX = if (textPosition == MFCharacterTextPosition.LEFT) -1F else 1F,
                                    scaleY = 1F
                                ),
                            painter = painterResource(id = R.drawable.mf_dialog_icon),
                            contentDescription = contentDescription,
                        )
                        MFText(
                            text = msg.value,
                            size = MFTexSizes.SMALL
                        )
                    }
                }
            }
        }
    }

}

private fun getDirection(mfCharacterTextPosition: MFCharacterTextPosition) : LayoutDirection {
    return when(mfCharacterTextPosition) {
        MFCharacterTextPosition.LEFT -> LayoutDirection.Rtl
        MFCharacterTextPosition.RIGHT -> LayoutDirection.Ltr
    }
}