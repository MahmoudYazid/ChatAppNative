package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ViewModel.ViewModelClass
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.view.TitleRow.Composeable_MymsgsBox
import com.example.myapplication.view.TitleRow.PeopleScroller
import com.example.myapplication.view.TitleRow.TitleRowCompose
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import okhttp3.internal.wait

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var ViewModelClassInst:ViewModelClass=ViewModelClass(this);
        setContent {
            MyApplicationTheme {
                Scaffold(
                    floatingActionButton = {
                        Column {
                            FloatingActionButton(onClick = {
                                Firebase.auth.signOut()

                                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                            }) {
                                Icon(imageVector = Icons.Rounded.ExitToApp, contentDescription ="" )
                            }
                            Box(modifier =
                            Modifier
                                .height(10.dp))
                            FloatingActionButton(onClick = {

                                startActivity(Intent(this@MainActivity,MainActivity::class.java))
                            }) {
                                Icon(imageVector = Icons.Rounded.Refresh, contentDescription ="" )
                            }
                        }

                    },

                ) {it
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(0xFF1B202D),

                        ) {


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(Color(0xFF1B202D))
                        ) {
                            TitleRowCompose("People")
                            PeopleScroller(ViewModelClassInst,this@MainActivity)
                            TitleRowCompose("Messages")
                            Composeable_MymsgsBox()

                        }

                    }
                }
                // A surface container using the 'background' color from the theme

            }
        }
    }
}
