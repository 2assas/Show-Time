package com.hamdy.showtime.ui.ui.favorite.ui.fragment.favorite_person.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamdy.showtime.ui.ui.favorite.model.FavoriteItem
import kotlinx.coroutines.tasks.await

class FavoritePersonRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun getFavorite():ArrayList<FavoriteItem> {
        val arr=ArrayList<FavoriteItem>()
        val collectionReference =
            db.collection("Users").document(auth.currentUser?.uid.toString())
                .collection("FavoritePersons")
        val result=collectionReference.get().await()
        for (i in result)
            arr.add(i.toObject(FavoriteItem::class.java))
        return arr
    }

}