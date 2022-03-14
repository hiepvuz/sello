package com.example.sello.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sello.entity.Cart
import com.example.sello.repository.CartRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartRepository = CartRepository(application)


    fun addToCart(cart: Cart): Disposable =
        cartRepository.addToCart(cart).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()


    fun getAllCart(personID: String) = cartRepository.getAllCart(personID)
    fun searchCart(personID: String) = cartRepository.searchCart(personID)

    fun deleteCart(personID: String, productID: String): Disposable? =
        cartRepository.deleteCart(personID, productID).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()


    fun searchListCartCheckout(personID: String) = cartRepository.searchListCartCheckout(personID)

    fun updateCartNumber(quantity: Int, personID: String, productID: String): Disposable? =
        cartRepository.updateCartNumber(quantity, personID, productID).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()


    fun checkoutCart(id: String): Disposable? = cartRepository.checkoutCart(id).subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {}
        .subscribe()


    fun updateCartCheckout(personID: String, productID: String): Disposable? =
        cartRepository.updateCartCheckout(personID, productID).subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {}
            .subscribe()

}