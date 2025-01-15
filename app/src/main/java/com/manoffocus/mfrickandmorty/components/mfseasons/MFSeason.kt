package com.manoffocus.mfrickandmorty.components.mfseasons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manoffocus.mfrickandmorty.R
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFTexSizes
import com.manoffocus.mfrickandmorty.components.mftextcomponents.MFText
import com.manoffocus.mfrickandmorty.models.seasons.Season
import com.manoffocus.mfrickandmorty.ui.theme.horizontalPaddingBg
import com.manoffocus.mfrickandmorty.ui.theme.mfHomeScreenContenColor
import com.manoffocus.mfrickandmorty.ui.theme.mfHomeScreenLabelColor

@Composable
fun MFSeasons(
    modifier: Modifier,
    seasons: List<Season>,
    onSeasonClick : (Season) -> Unit
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(seasons){ season ->
            MFSeason(modifier = modifier, season = season){
                onSeasonClick.invoke(season)
            }
        }
    }
}
@Preview
@Composable
fun MFSeason(
    modifier: Modifier = Modifier,
    season: Season = Season("Pilot", "22-22-22", "S01E01"),
    onChoose: (String) -> Unit = {}
){
    Card(
        modifier = Modifier
            .height(230.dp)
            .width(400.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onChoose.invoke(season.firsEpisodeCode) },
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 5.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.mf_rick_and_morty_season_background),
            contentDescription = stringResource(id = R.string.mf_cd_home_bg),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.7F)
                    .padding(horizontal = horizontalPaddingBg)
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = mfHomeScreenLabelColor
                        )
                    ){
                        append(text = stringResource(id = R.string.mf_home_screen_season_label))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal,
                            color = mfHomeScreenContenColor
                        )
                    ){
                        append(text = " ${season.firsEpisodeCode[2]}")
                    }
                })
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.onPrimary.copy(0.08F)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Label(
                        label = R.string.mf_home_screen_first_episode_label,
                        txt = season.firstEpisodeName)
                    Label(
                        label = R.string.mf_home_screen_release_date_label,
                        txt = season.firstEpisodeDate)
                }
            }
        }
    }
}

@Composable
fun Label(label: Int, txt: String){
    Row(
        modifier = Modifier
            .padding(horizontal = horizontalPaddingBg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        MFText(
            text = stringResource(id = label),
            color = mfHomeScreenLabelColor,
            weight = FontWeight.Bold,
            size = MFTexSizes.SMALL
        )
        MFText(
            modifier = Modifier.padding(end = horizontalPaddingBg),
            text = txt,
            color = mfHomeScreenContenColor,
            weight = FontWeight.Bold,
            size = MFTexSizes.XSMALL
        )
    }
}