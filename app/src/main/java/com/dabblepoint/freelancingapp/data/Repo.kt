package com.dabblepoint.freelancingapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.models.User
import com.dabblepoint.freelancingapp.models.Project
import com.dabblepoint.freelancingapp.models.WorkRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.*
import kotlin.collections.ArrayList

class Repo{


    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val TAG: String = "Repo"
    private val projectsLiveData: MutableLiveData<ArrayList<Project>> = MutableLiveData()
    private val createdBrojectsLiveData: MutableLiveData<ArrayList<Project>> = MutableLiveData()

    fun createUser(name: String, mob: String, role: String, status: MutableLiveData<RequestStatus>){


        val user = User(name, mob, role)
        status.postValue(RequestStatus(RequestStatus.Status.REQUESTED))
        db.collection("users").document(mob)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {

                Log.d(TAG, "user added")
                status.postValue(
                    RequestStatus(
                        RequestStatus.Status.SUCCESS,
                        user
                    )
                )

            }.addOnFailureListener{

                exception -> Log.d(TAG, "user not added")
                status.postValue(RequestStatus(RequestStatus.Status.FAILURE))
            }
    }


    fun addProject(title: String, createdBy: String, skills: ArrayList<String>, status: MutableLiveData<RequestStatus>){
        status.postValue(RequestStatus(RequestStatus.Status.REQUESTED))
        db.collection("projects")
            .add(mapOf("title" to title, "created_by" to createdBy, "skills" to skills, "date_created" to Date()))
            .addOnSuccessListener {

                Log.d(TAG, "user added")
                status.postValue(RequestStatus(RequestStatus.Status.SUCCESS))

            }.addOnFailureListener{

                    exception -> Log.d(TAG, "user not added")
                status.postValue(RequestStatus(RequestStatus.Status.FAILURE))
            }
    }

    fun makeWorkRequest(user: User, project: Project, status: MutableLiveData<RequestStatus>){
        status.postValue(RequestStatus(RequestStatus.Status.REQUESTED))
        db.collection("project_requests")
            .document(user.mob+"_" + project.id)
            .set(mapOf("user_id" to user.mob,
                "project_id" to project.id,
                "employer_rating" to 0f,
                "employee_rating" to 0f,
                "completed" to false,
                "assigned" to false,
                "date" to Date()))
            .addOnSuccessListener {

                Log.d(TAG, "user added")
                status.postValue(RequestStatus(RequestStatus.Status.SUCCESS))

            }.addOnFailureListener{

                    exception -> Log.d(TAG, "user not added")
                status.postValue(RequestStatus(RequestStatus.Status.FAILURE))
            }
    }

    fun rateEmployee(workRequest: WorkRequest, rating: Long, status:  MutableLiveData<RequestStatus>){

        status.postValue(RequestStatus(RequestStatus.Status.REQUESTED))
        db.collection("project_requests")
            .document(workRequest.request_id)
            .set(mapOf("employee_rating" to rating))
            .addOnSuccessListener {

                Log.d(TAG, "user added")
                status.postValue(RequestStatus(RequestStatus.Status.SUCCESS))

            }.addOnFailureListener{

                    exception -> Log.d(TAG, "user not added")
                status.postValue(RequestStatus(RequestStatus.Status.FAILURE))
            }
    }


    fun rateEmployer(workRequest: WorkRequest, rating: Long, status:  MutableLiveData<RequestStatus>){

        status.postValue(RequestStatus(RequestStatus.Status.REQUESTED))
        db.collection("project_requests")
            .document(workRequest.request_id)
            .set(mapOf("employer_rating" to rating))
            .addOnSuccessListener {

                Log.d(TAG, "user added")
                status.postValue(RequestStatus(RequestStatus.Status.SUCCESS))

            }.addOnFailureListener{

                    exception -> Log.d(TAG, "user not added")
                status.postValue(RequestStatus(RequestStatus.Status.FAILURE))
            }
    }

    fun getProjects(): MutableLiveData<ArrayList<Project>>{

        val works = arrayListOf<Project>()
        db.collection("projects")
            .addSnapshotListener{ documents, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (document in documents!!) {
                    val work = Project(
                        document.id,
                        document.get("title").toString(),
                        document.get("skills") as ArrayList<String>,
                        document.get("date_created") as Date
                    )

                    works.add(work)

                    db.collection("users").document(document.data.get("created_by").toString()).get()
                        .addOnSuccessListener {
                                documentSnapshot ->

                            val user = User(
                                documentSnapshot.data?.get("name").toString(),
                                documentSnapshot.data?.get("mob").toString(),
                                documentSnapshot.data?.get("role").toString(),
                                documentSnapshot.data?.get("rating") as Number,
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

    fun getProjectsCreatedBy(
        user: User,
        status: MutableLiveData<RequestStatus>
    ): MutableLiveData<ArrayList<Project>>{
        val projects = arrayListOf<Project>()
        db.collection("projects").whereEqualTo("created_by", user.mob)
            .addSnapshotListener{ documents, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)

                    return@addSnapshotListener
                }

                for (document in documents!!) {
                    val project = Project(
                        document.id,
                        document.get("title").toString(),
                        document.get("skills") as ArrayList<String>,
                        document.get("date_created") as Date
                    )
                    projects.add(project)
                    getWorkRequests(project)
                }

                createdBrojectsLiveData.postValue(projects)

            }

        return createdBrojectsLiveData
    }

    fun getWorkRequests(project: Project){

        val workRequests = arrayListOf<WorkRequest>()
        db.collection("project_requests")
            .whereEqualTo("project_id", project.id)
            .addSnapshotListener{ documents, e ->

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for (document in documents!!) {

                    val workRequest = WorkRequest(
                        request_id = document.id,
                        project = project,
                        employee_rating = document.get("employee_rating") as Number,
                        employer_rating = document.get("employer_rating") as Number,
                        assigned = document.get("assigned") as Boolean,
                        completed = document.get("completed") as Boolean)

                    workRequests.add(workRequest)

                    db.collection("users").document(document.data.get("created_by").toString()).get()
                        .addOnSuccessListener {
                                documentSnapshot ->

                            documentSnapshot.data?.let{
                                val user = User(
                                    documentSnapshot.data?.get("name").toString(),
                                    documentSnapshot.data?.get("mob").toString(),
                                    documentSnapshot.data?.get("role").toString(),
                                    documentSnapshot.data?.get("rating") as Number,
                                    documentSnapshot.data?.get("skills") as ArrayList<String>
                                )

                                workRequest.user.postValue(user)
                                Log.d("works", workRequests.toString())
                            }

                        }
                }

                project.requests.postValue(workRequests)

            }
    }

}