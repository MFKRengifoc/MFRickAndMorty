package com.manoffocus.mfrickandmorty.components.mftextcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.ui.theme.ChocolatePuddingFontBold
import com.manoffocus.mfrickandmorty.ui.theme.darkOnPrimary
import com.manoffocus.mfrickandmorty.ui.theme.sidesPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.verticalPaddingBg

enum class MFTexTitleSizes(var size: TextUnit){
    LARGE(size = 25.sp),
    MEDIUM(size = 20.sp),
    SMALL(size = 15.sp);
}
enum class MFTexSizes(var size: TextUnit){
    XLARGE(size = 28.sp),
    LARGE(size = 25.sp),
    MEDIUM(size = 20.sp),
    SMALL(size = 15.sp),
    XSMALL(size = 12.sp);
}


@Composable
fun MFTextTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onPrimary,
    size: MFTexTitleSizes = MFTexTitleSizes.MEDIUM,
    underLine: Boolean = false,
    maxWidth : Dp = 200.dp,
    align: TextAlign = TextAlign.Center
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max)
    ){
        Text(
            modifier = modifier
                .widthIn(0.dp, maxWidth),
            text = text,
            fontSize = size.size,
            fontWeight = FontWeight.Bold,
            color = color,
            textAlign = align,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontFamily = ChocolatePuddingFontBold
            )
        )
        if (underLine){
            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = color))
        }
    }
}

@Composable
fun MFText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colors.onPrimary,
    size: MFTexSizes = MFTexSizes.MEDIUM,
    weight: FontWeight = FontWeight.Normal,
    align: TextAlign = TextAlign.Start,
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = size.size,
        color = color,
        textAlign = align,
        fontWeight = weight,
        overflow = TextOverflow.Ellipsis
    )
}

data class MFTextAppend(
    var text: String,
    var color: Color = darkOnPrimary,
    var weight: FontWeight = FontWeight.Normal
)

@Composable
fun MFTextAppends(
    modifier: Modifier = Modifier,
    textArr : List<MFTextAppend>,
    fontSizes: MFTexSizes = MFTexSizes.MEDIUM
) {
    Text(
        modifier = modifier
            .padding(top = verticalPaddingBg),
        text = buildAnnotatedString {
            for (text in textArr){
                withStyle(
                    style = SpanStyle(
                        fontSize = fontSizes.size,
                        fontWeight = text.weight,
                        color = text.color
                    )
                ){
                    append(text = text.text)
                }
            }
        },
        textAlign = TextAlign.Center,
    )
}

@Composable
fun MFComplexHeader(
    modifier: Modifier = Modifier,
    title: String,
    actionText: String?,
    onClickAction: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = verticalPaddingBg,
                end = sidesPaddingBg
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MFTextTitle(
            modifier = Modifier.padding(start = sidesPaddingBg),
            text = title
        )
        if (actionText != null) {
            Card(
                modifier = Modifier
                    .padding(end = sidesPaddingBg)
                    .clickable { onClickAction.invoke() },
                elevation = 8.dp,
                shape = RoundedCornerShape(6.dp),
                backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.8F)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = sidesPaddingBg, vertical = verticalPaddingBg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MFText(
                        text = actionText,
                        size = MFTexSizes.SMALL
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.mf_right_arrow_icon),
                        contentDescription = "Arrow",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}