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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth

class LoginActivity : ComponentActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var GoogleSigninClient: GoogleSignInClient
    lateinit var ViewModelInst:ViewModelClass

    public fun SigninWithGoogle(){
        val signinIntent = GoogleSigninClient.signInIntent
        launcher.launch(signinIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {result->
        if(result.resultCode == Activity.RESULT_OK){
            val Task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(Task)
        }


    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account:GoogleSignInAccount?=task.result
            if (account !=null){

                updateUi(account)
            }
        }else{
            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun updateUi(account: GoogleSignInAccount) {
        val credintial =GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credintial).addOnCompleteListener {it->
            if (it.isSuccessful){

                Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()

                ViewModelInst.AddToFireBase(account.email.toString(),account.displayName.toString(),account.photoUrl.toString())

                val url:Intent= Intent(this,MainActivity::class.java)


                startActivity(url)
            }else{
                Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //google auth
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.GoogleAuthID))
            .requestEmail()
            .build()
        GoogleSigninClient= GoogleSignIn.getClient(this,gso)

        ViewModelInst=ViewModelClass(this)
        FirebaseApp.initializeApp(this)


        // google signin finished


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
                                SigninWithGoogle()
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


