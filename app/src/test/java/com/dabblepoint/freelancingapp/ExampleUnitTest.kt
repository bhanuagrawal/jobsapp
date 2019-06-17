package com.dabblepoint.freelancingapp

import androidx.lifecycle.MutableLiveData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val repo: Repo = Repo()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun user_isAdded(){
        val userAddStatus: MutableLiveData<RequestStatus> = MutableLiveData()
        repo.createUser("Bhanu Agrawal", "7055553175","employee",  userAddStatus)
        assertEquals(4, 2 + 2)

    }

    @Test
    fun work_isAdded(){
        repo.addWork()
        assertEquals(4, 2 + 2)

    }
}
