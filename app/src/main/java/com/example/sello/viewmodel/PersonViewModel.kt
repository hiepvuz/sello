package com.example.sello.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sello.entity.Person
import com.example.sello.repository.FireStoreRepository
import com.example.sello.repository.PersonRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val personRepository: PersonRepository = PersonRepository(application)
    private val fireStoreRepository = FireStoreRepository(application)


    fun insertPerson(person: Person) {
        personRepository.insertPerson(person).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()
    }

    fun checkPhonePass(phone: String, pass: String) =
        personRepository.checkPhonePassword(phone, pass)

    fun findPersonByPhone(phone: String) = personRepository.findPersonByPhone(phone)

    private fun updatePerson(person: Person) =
        personRepository.updatePerson(person).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()

    fun deletePerson(phone: String): Disposable? =
        personRepository.deletePerson(phone).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()

    fun addFireStore(person: Person) = fireStoreRepository.addFireStore(person)

    fun getDataFromFireStore() = viewModelScope.launch() {
        val list = fireStoreRepository.getDataFromFireStore()
        list.forEach { person -> personRepository.insertPerson(person) }
    }

    fun updateDataToFireStore(person: Person) = viewModelScope.launch(Dispatchers.IO) {
        fireStoreRepository.updateDataToFireStore(person)
        updatePerson(person)
    }

    fun deletePersonFromFireStore(person: Person) =
        fireStoreRepository.deletePersonFromFireStore(person)
}