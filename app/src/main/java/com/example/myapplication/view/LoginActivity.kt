package com.example.myapplication.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.ViewModel.ViewModelClass
import com.example.myapplication.model.user
import com.example.myapplication.view.ui.theme.MyApplicationTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginActivity : ComponentActivity() {

    lateinit var ViewModelInst:ViewModelClass





    suspend fun signUpAndSignIn(email: String, password: String): Boolean {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            // Sign-up successful
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            // Now you can sign in

            // Sign-in successful
            true
        } catch (e: FirebaseAuthUserCollisionException) {
            // Handle user already exists (email collision) separately
            // Display a message to the user, or redirect them to a different flow
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            ViewModelInst.AddToFireBase(Firebase.auth.currentUser?.email.toString())
            startActivity(Intent(this@LoginActivity,MainActivity::class.java))

            true
        } catch (e: FirebaseAuthException) {
            // Handle other authentication errors
            // Display a message to the user or log the error for debugging
            false
        }
    }


    suspend fun SignOut() {
            Firebase.auth.signOut()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //google auth


        ViewModelInst=ViewModelClass(this)


        // google signin finished


        setContent {
            var EmailInput by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF1B202D)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        TextField(value = EmailInput,
                            onValueChange ={
                                           EmailInput=it
                            } ,
                            modifier =
                            Modifier
                                .fillMaxWidth(),
                            label = { Text("Email") },

                           )
                        Box (modifier = Modifier.height(20.dp)){}
                        TextField(value = password,
                            onValueChange ={
                                password=it
                            } ,
                            modifier =
                            Modifier
                                .fillMaxWidth(),
                            label = { Text("Password") },

                        )
                        Box (modifier = Modifier.height(20.dp)){}
                        Button(
                            onClick = {
                                lifecycleScope.launch {
                                    val success = signUpAndSignIn(EmailInput, password)

                                }
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)

                        ) {
                          Text(text = "Signin/Signup")

                        }
                        // separate box
                        Box (modifier = Modifier.height(20.dp)){}


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
    }


}


