package com.example.myapplication.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import com.example.myapplication.model.msgsDataClass
import com.example.myapplication.model.user
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
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





    fun getmsgsFromFirestore(listener: (MutableList<msgsDataClass>?) -> Unit) {
        val collection = db.collection("msgs")

        // Add a snapshot listener to listen for real-time updates
        collection
            .orderBy("date")
            .addSnapshotListener { querySnapshot, _ ->
                if (querySnapshot != null) {
                    val listOfData = mutableListOf<msgsDataClass>()

                    for (document in querySnapshot.documents) {
                        val msg_ = document.getString("msg") ?: ""
                        val reciever_ = document.getString("reciever") ?: ""
                        val sender_ = document.getString("sender") ?: ""
                        listOfData.add(msgsDataClass(sender = sender_, reciever = reciever_, msg = msg_))
                    }

                    listener(listOfData) // Notify the listener with the updated data
                }
            }
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

    fun getRealtimeMyChatsFromFirestore(listener: (List<user>) -> Unit) {
        val listOfData: MutableList<user> = mutableListOf()

        val chatsDB = db.collection("chats")
        val usersDB = db.collection("user")

        // Add a snapshot listener to listen for real-time updates
        chatsDB
            .where(
                Filter.or(
                    Filter.equalTo("p1", Firebase.auth.currentUser?.email),
                    Filter.equalTo("p2", Firebase.auth.currentUser?.email)
                )
            )

            .addSnapshotListener { querySnapshot, _ ->
                if (querySnapshot != null) {
                    listOfData.clear() // Clear existing data before updating

                    for (chatDocument in querySnapshot.documents) {
                        val otherUserEmail = if (chatDocument.getString("p1").toString() == Firebase.auth.currentUser?.email.toString()) {
                            chatDocument.getString("p2")
                        } else {
                            chatDocument.getString("p1")
                        }

                        if (otherUserEmail != null) {
                            usersDB.where(
                                Filter.equalTo("email", otherUserEmail)
                            ).get().addOnSuccessListener { userDocuments ->
                                for (userDocument in userDocuments.documents) {
                                    val email = userDocument.getString("email") ?: ""
                                    val username = userDocument.getString("username") ?: ""
                                    val img_ = userDocument.getString("img") ?: ""
                                    val id_ = userDocument.getString("id") ?: ""
                                    listOfData.add(user(email = email, username = username, img = img_, id = id_))
                                }

                                listener(listOfData) // Notify the listener with the updated data
                            }
                        }
                    }
                }
            }
    }

}


