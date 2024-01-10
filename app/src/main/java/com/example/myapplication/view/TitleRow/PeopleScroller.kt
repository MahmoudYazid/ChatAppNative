package com.example.myapplication.view.TitleRow

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapplication.R


@Composable
fun PeopleScroller(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp)


            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,



    ){
        Scroller()
    }

}

@Composable
fun Scroller(

){
    for (x in 1..10){
        Box (


        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Image(painter = rememberAsyncImagePainter(
                  model = "https://s3-alpha-sig.figma.com/img/2de2/a756/35286e92de0ebf87910ac0d4e14dc961?Expires=1705881600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=WiJtTdyY5I0P9echSbL0KwYe-6MZJYcLl04e2KePxElElv8goXCO3IM1IdhdgO12YYqSTqIJuw0Oq6AGW5efy4wCys7spQ~jWiwtu61X3Jx8L8NcPO69VK5Z3I~FcXVyKkb5k-RSIoCvPJq76SLzGztTAsK8NTJMIsSLEpNyxvhxyWKAcD-NBcrYSPpOitdVmfC6wMhh8OPjAe78aBNEaGUcAcCUyXiX8MzjzeRFmyvVIwmXwSZGmn1VwOVgd8ni3HwIz8CvS~11toG73baGlaU-EuM3wRK3IafdUSte8zmpNM15RfJbUf-oLVnfBDwKx5v37GrilkzHe2MFeJTg5w__"),
                  contentDescription = "sss",

                  modifier = Modifier
                      .size(60.dp)
                      .clip(shape = RoundedCornerShape(50.dp))
              )
                Text(text = "name",
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                    fontSize = 16.sp)
            }
            
        }
        //spacing box
        Box (
            modifier = Modifier
                .width(10.dp)
        ){

        }
  

    }
}