package com.example.myapplication.view

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.ViewModel.ViewModelClass
import com.example.myapplication.model.msgsDataClass
import com.example.myapplication.model.user
import com.example.myapplication.view.ui.theme.MyApplicationTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ChatBox : ComponentActivity() {
    lateinit var GoogleSigninClient: GoogleSignInClient
    lateinit var ViewModelInst: ViewModelClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val firebaseAuth = Firebase.auth
        ViewModelInst = ViewModelClass(this)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF292F3F)
                ) {
                    Box {
                        Column {
//                            Text(text = firebaseAuth.currentUser?.displayName.toString())
//                            Text(text = "${intent.getStringExtra("his_username")}")
//
//                            TalkToWhoPart(intent.getStringExtra("his_username").toString(),intent.getStringExtra("his_img").toString())
//                            MsgsColumn()
//                            SendMsgBox()

                            Box(
                                modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f)


                            ) {
                                TalkToWhoPart(
                                    intent.getStringExtra("his_username").toString(),
                                    intent.getStringExtra("his_img").toString()
                                )
                            }
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(7f)


                            ) {
                                MsgsColumn(ViewModelInst)
                            }
                            Box(
                                modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f)


                            ) {
                                SendMsgBox(

                                    ViewModelInst,
                                    firebaseAuth.currentUser?.email.toString(),
                                    intent.getStringExtra("his_email").toString()

                                )
                            }


                        }
                    }

                }
            }
        }
    }
}


// part1
@Composable
fun TalkToWhoPart(PartnerName: String, PartnerImg: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFF292F3F))
            .padding(start = 8.dp, top = 3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "${PartnerImg}"
            ),
            contentDescription = "sss",

            modifier = Modifier
                .size(50.dp)
                .clip(shape = RoundedCornerShape(80.dp))
        )


        Box(
            modifier = Modifier
                .width(30.dp)
        )
        Text(text = "${PartnerName}", color = Color.White)

    }

}


@Composable
fun MsgsColumn(Viewmodel: ViewModelClass) {

    val AllData = remember {
        mutableStateOf<MutableList<msgsDataClass>?>(
            null
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        AllData.value = Viewmodel.getmsgsFromFirestore()

    })

    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFF292F3F))
            .padding(5.dp, 5.dp)
            .verticalScroll(rememberScrollState())


    ) {

        AllData.value?.forEach { it
            Box(modifier = Modifier.height(5.dp))
            if (it.sender.toString()==FirebaseAuth.getInstance().currentUser?.email){
                Row (
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                    ,
                    horizontalArrangement = Arrangement.End
                ){

                    Box (
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(50.dp))
                            .fillMaxWidth(0.8f)
                            .background(Color(0xFF7A8194))
                            .align(Alignment.CenterVertically)
                            .padding(20.dp)

                    ){
                        Text(text = it.msg,
                            color = Color.White,
                            fontWeight = FontWeight.W400,
                            fontSize = 20.sp)
                    }
                    

                }
            }else{

                Row (
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Start
                ){

                    Box (
                        modifier = Modifier
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(50.dp))
                            .fillMaxWidth(0.5f)
                            .background(Color.Blue)

                    ){

                    }

                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMsgBox(ViewModelInst: ViewModelClass, sender: String, reciever: String) {


    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 10.dp)
            .background(Color(0xFF292F3F))
    ) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .background(Color(0xFF3D4354)),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White), // Set your desired text color
            shape = RoundedCornerShape(16.dp),


            trailingIcon = {
                Icon(imageVector = Icons.Filled.Send, contentDescription = "dddd",
                    tint = Color(0xFF9398A7),
                    modifier = Modifier.clickable {
                        ViewModelInst.SendMsg(
                            msgInput = text.toString(),
                            sender = sender,
                            reciever = reciever
                        )
                    }

                )
            },


            )
    }
}