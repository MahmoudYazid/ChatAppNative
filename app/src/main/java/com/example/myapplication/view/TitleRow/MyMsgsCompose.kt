package com.example.myapplication.view.TitleRow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun Composeable_MymsgsBox(){
    Surface(

        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            shape = RoundedCornerShape(topEnd = 50.dp, topStart = 50.dp),
            color = Color(0xFF292F3F)



    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp)
        ) {
            for (x in 1..20){
            Card()
            }
        }

    }
}

@Composable
fun Card(){
    Surface(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .height(50.dp),
        color = Color(0xFF292F3F)


    ) {
        Row (
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            //img
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.2f)

            ){
                Image(painter = rememberAsyncImagePainter(
                    model = "https://s3-alpha-sig.figma.com/img/2de2/a756/35286e92de0ebf87910ac0d4e14dc961?Expires=1705881600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=WiJtTdyY5I0P9echSbL0KwYe-6MZJYcLl04e2KePxElElv8goXCO3IM1IdhdgO12YYqSTqIJuw0Oq6AGW5efy4wCys7spQ~jWiwtu61X3Jx8L8NcPO69VK5Z3I~FcXVyKkb5k-RSIoCvPJq76SLzGztTAsK8NTJMIsSLEpNyxvhxyWKAcD-NBcrYSPpOitdVmfC6wMhh8OPjAe78aBNEaGUcAcCUyXiX8MzjzeRFmyvVIwmXwSZGmn1VwOVgd8ni3HwIz8CvS~11toG73baGlaU-EuM3wRK3IafdUSte8zmpNM15RfJbUf-oLVnfBDwKx5v37GrilkzHe2MFeJTg5w__"),
                    contentDescription = "sss",

                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = RoundedCornerShape(100.dp))
                )
            }

            Box(modifier = Modifier.width(10.dp))
            // Column [Name , last msg ]
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f)

            ) {
                Text(text = "name", fontSize = 20.sp, fontWeight = FontWeight.W600, color = Color.White)
                Text(text = "Last Msg", fontSize = 14.sp, fontWeight = FontWeight.W400 , color = Color(0xFFB3B9C9))

            }

            // CLast Msg Date

            Column(
                modifier = Modifier
                    .weight(.2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Thu", fontSize = 14.sp, fontWeight = FontWeight.W400 , color = Color(0xFFB3B9C9))

            }


        }

    }
}