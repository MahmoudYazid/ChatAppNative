package com.example.myapplication.view.TitleRow

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ViewModel.ViewModelClass
import com.example.myapplication.model.msgsDataClass
import com.example.myapplication.model.user
import com.example.myapplication.view.ChatBox

@Composable
fun Composeable_MymsgsBox(){
    val Viewmodel:ViewModelClass = ViewModelClass(LocalContext.current)
    val context = LocalContext.current

    val AllData = remember {
        mutableStateOf<List<user>?>(null)
    }

    LaunchedEffect(key1 = Unit) {
        Viewmodel.getRealtimeMyChatsFromFirestore { updatedList ->
            AllData.value = updatedList

        }
    }
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
            AllData.value?.forEach {
                Box(modifier = Modifier
                    .clickable {
                        val Inent= Intent(context, ChatBox::class.java)

                        Inent.putExtra("his_email", it.email.toString(),
                        )





                        context.startActivity(Inent)
                    }){
                    Card(it.email.toString())

                }
            }


            }
        }


}

@Composable
fun Card(Email:String){
    Surface(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .height(70.dp)

        ,
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
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription ="ss",
                    tint = Color.White
                    ,modifier = Modifier
                        .size(60.dp)
                        .clip(shape = CircleShape))
            }

            Box(modifier = Modifier.width(10.dp))
            // Column [Name , last msg ]
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f)

            ) {
                Text(text = "$Email", fontSize = 20.sp, fontWeight = FontWeight.W600, color = Color.White)
                Text(text = "Last Msg-Hidden", fontSize = 14.sp, fontWeight = FontWeight.W400 , color = Color(0xFFB3B9C9))

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