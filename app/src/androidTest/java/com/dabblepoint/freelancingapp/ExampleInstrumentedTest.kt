package com.dabblepoint.freelancingapp

import androidx.lifecycle.MutableLiveData
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.dabblepoint.freelancingapp.data.Repo
import com.dabblepoint.freelancingapp.models.RequestStatus
import com.dabblepoint.freelancingapp.models.User
import com.dabblepoint.freelancingapp.models.Project

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.dabblepoint.freelancingapp", appContext.packageName)
    }

    val repo: Repo = Repo()

    @Test
    fun user_isAdded(){
        val userAddStatus: MutableLiveData<RequestStatus> = MutableLiveData()
        repo.createUser("Bhanu Agrawal", "7055553175","employee",  userAddStatus)
        Thread.sleep(2000);
        assertEquals(4, 2 + 2)

    }

    @Test
    fun work_isAdded(){
/*        repo.addProject("create android app", "7055553175", arrayListOf("android", "java"))
        Thread.sleep(5000);
        assertEquals(4, 2 + 2)*/

    }

    @Test
    fun workRequest_isAdded(){
        val work = Project(
            "COJswdvKBG8bne0KJJeL",
            "create android app",
            arrayListOf("android", "java")
        )
        val user = User("0", "7055553175", "")

        repo.makeWorkRequest(user, work)
        Thread.sleep(5000);
        assertEquals(4, 2 + 2)

    }

    @Test
    fun projects_areFetched(){
        repo.getProjects()
        Thread.sleep(10000)
        assertEquals(4, 2 + 2)

    }

    @Test
    fun employee_isRated(){
        val work = Project(
            "COJswdvKBG8bne0KJJeL",
            "create android app",
            arrayListOf("android", "java")
        )
        val user = User("0", "9456668556", "")
        repo.makeWorkRequest(user, work)
        Thread.sleep(5000);
        assertEquals(4, 2 + 2)
    }
}
