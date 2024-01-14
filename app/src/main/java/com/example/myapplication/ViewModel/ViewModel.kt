package com.example.myapplication.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import com.example.myapplication.model.msgsDataClass
import com.example.myapplication.model.user
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.HashMap

class ViewModelClass(context: Context) {
    private val db = FirebaseFirestore.getInstance()
    lateinit var context: Context

    init {
        this.context = context

    }

    fun AddToFireBase(
        emailInput: String,
        userNameInput: String,
        imgInput: String,
        IdOfUser: String
    ) {

        val newdata = HashMap<String, Any>()
        newdata["email"] = emailInput.toString()
        newdata["username"] = userNameInput.toString()
        newdata["img"] = imgInput.toString()
        newdata["id"] = IdOfUser.toString()

        db.collection("user").whereEqualTo("email", emailInput).get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 0) {

                    db.collection("user")
                        .add(newdata)

                }
            }
    }

    fun SendMsg(sender: String, reciever: String, msgInput: String) {

        val newmsg = HashMap<String, Any>()
        newmsg["msg"] = msgInput.toString()
        newmsg["reciever"] = reciever.toString()
        newmsg["sender"] = sender.toString()
        newmsg["date"] = LocalDateTime.now().toString()

        val newchatPerson = HashMap<String, Any>()
        newchatPerson["p1"] = sender.toString()
        newchatPerson["p2"] = reciever.toString()

        db.collection("chats").where(
            Filter.or(

                Filter.and(
                    Filter.equalTo("p2", sender),
                    Filter.equalTo("p1", reciever),

                    ),

                Filter.and(
                    Filter.equalTo("p1", sender),
                    Filter.equalTo("p2", reciever),

                    )


            )
        ).get().addOnSuccessListener { documents ->
            if (documents.size() == 0) {
                // if they didnt talk to eachother before we will open new chat
                db.collection("chats")
                    .add(newchatPerson)
                db.collection("msgs").add(newmsg)

            } else {
                // else we will just send the msg
                db.collection("msgs").add(newmsg)
            }
        }
    }



    suspend fun getmsgsFromFirestore(): MutableList<msgsDataClass> {
        val listOfData: MutableList<msgsDataClass> = mutableListOf()

        val collection = db.collection("msgs")

        collection
            .orderBy("date")
            .get().await().forEach { querySnapshot ->
            val msg_ = querySnapshot.getString("msg") ?: ""
            val reciever_ = querySnapshot.getString("reciever") ?: ""
            val sender_ = querySnapshot.getString("sender") ?: ""
            listOfData.add(msgsDataClass(sender = sender_, reciever =reciever_, msg = msg_))


        }
        return listOfData

    }

    suspend fun getAllUsersFromFirestore(): MutableList<user> {
        val listOfData: MutableList<user> = mutableListOf()

        val collection = db.collection("user")

        collection.get().await().forEach { querySnapshot ->
            val email = querySnapshot.getString("email") ?: ""
            val username = querySnapshot.getString("username") ?: ""
            val img_ = querySnapshot.getString("img") ?: ""
            val id_ = querySnapshot.getString("id") ?: ""
            listOfData.add(user(email = email, username = username, img = img_, id = id_))


        }
        return listOfData

    }


}


