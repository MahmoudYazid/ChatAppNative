package com.example.myapplication.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.view.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.net.URL

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF1B202D)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        //login - register  google btm
                        Button(
                            onClick = {
                                val url:Intent= Intent(this@LoginActivity,MainActivity::class.java)
                                startActivity(url)

                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                            
                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = "Profile Image",
                                modifier = Modifier.size(50.dp)
                            )

                        }

                        // separate box
                        Box (modifier = Modifier.height(10.dp)){}


                        //go to my linkedin btn
                        Button(
                            onClick = {
                                       val url:Intent= Intent(Intent.ACTION_VIEW,Uri.parse("https://www.linkedin.com/in/mahmoudyazid/"))
                                        startActivity(url)
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)

                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Profile Image",
                                modifier = Modifier.size(50.dp)
                            )

                        }

                    }


                }
            }
        }
    }}


