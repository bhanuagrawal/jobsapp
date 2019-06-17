package com.dabblepoint.freelancingapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*
import kotlin.collections.ArrayList

class Repo{




    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val projectsLiveData: MutableLiveData<ArrayList<Work>> = MutableLiveData()
    private val TAG: String = "Repo"
    fun createUser(name: String, mob: String, role: String, userAddStatus: MutableLiveData<RequestStatus>){

        userAddStatus.postValue(RequestStatus(RequestStatus.Status.REQUESTED))
        db.collection("users").document(mob)
            .set((User(name, mob, role)), SetOptions.merge())
            .addOnSuccessListener {
                Log.d(TAG, "user added")
                userAddStatus.postValue(RequestStatus(RequestStatus.Status.SUCCESS))

            }.addOnFailureListener{

                exception -> Log.d(TAG, "user not added")
                userAddStatus.postValue(RequestStatus(RequestStatus.Status.FAILURE))
            }
    }

    fun addWork(title: String, createdBy: String, skills: ArrayList<String>){
        db.collection("projects")
            .add(mapOf("title" to title, "createdBy" to createdBy, "skills" to skills, "date_created" to Date()))
            .addOnSuccessListener {
                Log.d(TAG, "user added")
            }.addOnFailureListener{
                    exception -> Log.d(TAG, "user not added")
            }
    }

    fun makeWorkRequest(user: User, project: Work){
        db.collection("project_requests")
            .document(user.mob+"_" + project.work_id)
            .set(mapOf("user_id" to user.mob,
                "project_id" to project.work_id,
                "employer_rating" to 0f,
                "employee_rating" to 0f,
                "completed" to false,
                "assigned" to false,
                "date" to Date()))
            .addOnSuccessListener {
                Log.d(TAG, "user added")
            }.addOnFailureListener{
                    exception -> Log.d(TAG, "user not added")
            }
    }

    fun rateEmployee(workRequrdt: WorkRequest, rating: Long){

        db.collection("project_requests")
            .document(workRequrdt.request_id)
            .set(mapOf("employee_rating" to rating))
            .addOnSuccessListener {
                Log.d(TAG, "user added")
            }.addOnFailureListener{
                    exception -> Log.d(TAG, "user not added")
            }

    }

    fun getProjects(): MutableLiveData<ArrayList<Work>>{

        val works = arrayListOf<Work>()
        db.collection("projects")
            .addSnapshotListener{ documents, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (document in documents!!) {
                    val work = Work(document.id,
                        document.get("title").toString(),
                        document.get("skills") as ArrayList<String>,
                        document.get("date_created") as Date)

                    works.add(work)

                    db.collection("users").document(document.data.get("created_by").toString()).get()
                        .addOnSuccessListener {
                                documentSnapshot ->

                            val user = User(documentSnapshot.data?.get("name").toString(),
                                documentSnapshot.data?.get("mob").toString(),
                                documentSnapshot.data?.get("role").toString(),
                                documentSnapshot.data?.get("rating").toString(),
                                documentSnapshot.data?.get("skills") as ArrayList<String>
                            )

                            work.created_by.postValue(user)
                            Log.d("works", works.toString())

                        }
                }

                projectsLiveData.postValue(works)

            }


        return projectsLiveData
    }




}