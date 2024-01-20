package com.example.myapplication.view.TitleRow

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.myapplication.model.user
import com.example.myapplication.view.ChatBox
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun PeopleScroller(Viewmodel:ViewModelClass,context:Context){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp)


            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,



    ){

        Scroller(Viewmodel,context)
    }

}

@Composable
fun Scroller(Viewmodel: ViewModelClass, context: Context) {

    val AllData = remember {
        mutableStateOf<MutableList<user>?>(
            null
        )
    }


    LaunchedEffect(key1 = Unit, block ={
        AllData.value = Viewmodel.getAllUsersFromFirestore()

    } )

   AllData.value?.forEach{it
        Box (
        modifier = Modifier
            .clickable {
                val Inent=Intent(context,ChatBox::class.java)
                Inent.putExtra("his_username", it.username.toString(),
                )
                Inent.putExtra("his_img", it.img.toString(),
                )

                Inent.putExtra("his_email", it.email.toString(),
                )
                Inent.putExtra("his_id", it.id.toString(),
                )





                context.startActivity(Inent)
            }

        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = rememberAsyncImagePainter(
                    model = "${it.img}"),
                    contentDescription = "sss",

                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = RoundedCornerShape(50.dp))
                )
                Text(text = "${it.username}",
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