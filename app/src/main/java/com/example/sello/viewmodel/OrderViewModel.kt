package com.example.sello.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sello.entity.Order
import com.example.sello.entity.OrderItem
import com.example.sello.repository.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val orderRepository = OrderRepository(application)
    private val fireStoreRepository = FireStoreRepository(application)



    fun insertOrderItem(orderItem: OrderItem): Disposable? = orderRepository.insertOrderItem(orderItem).subscribeOn(
        Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {}
        .subscribe()

    fun insertOrder(order: Order): Disposable? = orderRepository.insertOrder(order).subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {}
        .subscribe()


    fun addOrderItemToFireStore(orderItem: OrderItem,timeIdOrder : String) = fireStoreRepository.addOrderItemToFireStore(orderItem, timeIdOrder)

}