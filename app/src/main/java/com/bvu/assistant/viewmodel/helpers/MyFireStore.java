package com.bvu.assistant.viewmodel.helpers;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyFireStore {

    public static void init() {
        FirebaseFirestore instance = FirebaseFirestore.getInstance();

        DocumentReference reference = instance.collection("students").document("18033747");
        Log.d("TAG", "init: ");
    }

}
