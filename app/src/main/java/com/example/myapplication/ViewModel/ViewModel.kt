package com.example.myapplication.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.model.msgsDataClass
import com.example.myapplication.model.user
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.HashMap
import kotlin.math.log

class ViewModelClass(context: Context) {
    private val db = FirebaseFirestore.getInstance()
    lateinit var context: Context

    init {
        this.context = context

    }

    fun AddToFireBase(
        emailInput: String,


    ) {

        val newdata = HashMap<String, Any>()
        newdata["email"] = emailInput.toString()



        db.collection("user").whereEqualTo("email", emailInput).get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 0) {

                    db.collection("user")
                        .add(newdata)

                }
            }
    }



 






    fun getmsgsFromFirestore(listener: (MutableList<msgsDataClass>?) -> Unit,Id:String) {
        val collection = db.collection("msgs")

        // Add a snapshot listener to listen for real-time updates
        collection
            .orderBy("date",Query.Direction.ASCENDING)

            .addSnapshotListener { querySnapshot, _ ->
                if (querySnapshot != null) {
                    val listOfData = mutableListOf<msgsDataClass>()

                    for (document in querySnapshot.documents) {
                        val msg_ = document.getString("msg") ?: ""
                        val reciever_ = document.getString("reciever") ?: ""
                        val sender_ = document.getString("sender") ?: ""
                        if (document.getString("id")==Id){
                            listOfData.add(msgsDataClass(sender = sender_, reciever = reciever_, msg = msg_))

                        }
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

            listOfData.add(user(email = email))


        }

        return listOfData

    }

    fun getRealtimeMyChatsFromFirestore(listener: (List<user>) -> Unit) {
        val listOfData: MutableList<user> = mutableListOf()

        val chatsDB = db.collection("chats")
        val usersDB = db.collection("user")

        // Add a snapshot listener to listen for real-time updates
        chatsDB


            .addSnapshotListener { querySnapshot, _ ->
                if (querySnapshot != null) {
                    listOfData.clear() // Clear existing data before updating

                    for (chatDocument in querySnapshot.documents) {
                        var otherUserEmail :String?=null

                        if (chatDocument.getString("p1").toString() == Firebase.auth.currentUser?.email.toString()){
                            otherUserEmail= chatDocument.getString("p2").toString()

                        }
                        if (chatDocument.getString("p2").toString() == Firebase.auth.currentUser?.email.toString()){
                            otherUserEmail= chatDocument.getString("p1").toString()

                        }

                        if (otherUserEmail != null) {
                            usersDB.where(
                                Filter.equalTo("email", otherUserEmail)
                            ).get().addOnSuccessListener { userDocuments ->
                                for (userDocument in userDocuments.documents) {
                                    val email = userDocument.getString("email") ?: ""

                                    if (!listOfData.contains(user(email = email))) {
                                        listOfData.add(user(email = email))
                                    }



                                }

                                listener(listOfData) // Notify the listener with the updated data
                            }
                        }
                    }
                }
            }
    }



    suspend fun MakeChat(P1Input: String, P2Input: String) {
        val newdata = HashMap<String, Any>()
        newdata["p1"] = P1Input.toString()
        newdata["p2"] = P2Input.toString()

        val chatsDB = db.collection("chats")
        val nextId = chatsDB.get().await().size() + 1
        newdata["id"] = nextId.toString()

        val count = chatsDB
            .where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("p1", P1Input),
                        Filter.equalTo("p2", P2Input)
                    ),
                    Filter.and(
                        Filter.equalTo("p2", P2Input),
                        Filter.equalTo("p1", P1Input)
                    )
                )
            ).get().await().size()

        if (count == 0) {
            chatsDB.add(newdata).await()
        }
    }

    suspend fun getChatId(P1Input: String, P2Input: String): String {
        val chatsDB = db.collection("chats")

        // Wait for the chat creation to complete
        MakeChat(P1Input, P2Input)

        // Add a snapshot listener to listen for real-time updates
        val Id = chatsDB
            .where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("p1", P1Input),
                        Filter.equalTo("p2", P2Input)
                    ),
                    Filter.and(
                        Filter.equalTo("p1", P2Input),
                        Filter.equalTo("p2", P1Input)
                    )
                )
            )
            .get()
            .await()
            .first()

        val Id_Get: String = Id.getString("id").toString()
        return Id_Get
    }


    suspend fun SendMsg(msg:String,Sender:String,Reciever:String) {
        val chatsDB = db.collection("chats")

        // Wait for the chat creation to complete

        // Add a snapshot listener to listen for real-time updates
        val Id = chatsDB
            .where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("p1", Reciever),
                        Filter.equalTo("p2", Sender)
                    ),
                    Filter.and(
                        Filter.equalTo("p1", Sender),
                        Filter.equalTo("p2", Reciever)
                    )
                )
            )
            .get()
            .await()
            .first()


        val collection = db.collection("msgs")
        val newmsg = HashMap<String, Any>()
        newmsg["sender"] = Sender.toString()
        newmsg["reciever"] = Reciever.toString()
        newmsg["msg"] = msg.toString()
        newmsg["id"] = Id.getString("id").toString()
        newmsg["date"] = LocalDateTime.now().toString()
        // Add a snapshot listener to listen for real-time updates
        collection.add(newmsg)



    }


}


