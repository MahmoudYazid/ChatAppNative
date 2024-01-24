package com.example.myapplication.view.TitleRow

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
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
    val usersList = remember {
        mutableStateOf<MutableList<user>?>(
            null
        )
    }


    LaunchedEffect(key1 = Unit, block ={
        usersList.value = Viewmodel.getAllUsersFromFirestore()

    } )
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp)


            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,



    ){

        usersList.value?.forEach {
            Item(Viewmodel,context,it.email.toString())

        }
    }

}

@Composable
fun Item(Viewmodel: ViewModelClass, context: Context,Email:String) {




        Box (
        modifier = Modifier
            .clickable {
                val Inent=Intent(context,ChatBox::class.java)

                Inent.putExtra("his_email", Email.toString(),
                )






                context.startActivity(Inent)
            }

        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val userImage: Unit =
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription ="ss",
                    tint = Color.White
                    ,modifier = Modifier
                        .size(60.dp)
                        .clip(shape = CircleShape))

                Text(text = Email.toString(),
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