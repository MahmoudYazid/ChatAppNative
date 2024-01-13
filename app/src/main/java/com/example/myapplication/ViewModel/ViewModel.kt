package com.example.myapplication.ViewModel

import android.content.Context
import com.example.myapplication.model.user
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.HashMap

class ViewModelClass (context:Context) {
    private val db = FirebaseFirestore.getInstance()
    lateinit var context:Context
    init{
        this.context = context

    }
    fun AddToFireBase(emailInput: String, userNameInput: String, imgInput: String){

        val newdata = HashMap<String,Any>()
        newdata["email"] = emailInput.toString()
        newdata["username"] = userNameInput.toString()
        newdata["img"] = imgInput.toString()

        db.collection("user").whereEqualTo("email",emailInput).get().addOnSuccessListener {
            documents->
            if (documents.size()==0){

                db.collection("user")
                    .add(newdata)

            }
        }
    }

suspend fun getAllUsersFromFirestore(): MutableList<user>  {
    val listOfData: MutableList<user> = mutableListOf()

    val collection =db.collection("user")

        collection.get().await().forEach { querySnapshot ->
            val email = querySnapshot.getString("email") ?: ""
            val username = querySnapshot.getString("username") ?: ""
            val img_ = querySnapshot.getString("img") ?: ""
            listOfData.add(user(email = email, username = username,img=img_))


    }
    return listOfData

}


}


