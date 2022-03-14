package com.example.sello.repository

import android.app.Application
import com.example.sello.dao.AppDao
import com.example.sello.database.AppDatabase
import com.example.sello.entity.Cart
import com.example.sello.entity.Order
import com.example.sello.entity.OrderItem
import io.reactivex.rxjava3.core.Completable

class CartRepository(application: Application) {

    private val appDao: AppDao

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(application)
        appDao = appDatabase.dao
    }

    fun addToCart(cart: Cart): Completable {
        return appDao.addToCart(cart)
    }

    fun getAllCart(personID: String) = appDao.getAllCart(personID)

    fun searchListCartCheckout(personID: String) = appDao.searchListCartCheckout(personID)

    fun searchCart(personID: String) = appDao.searchCart(personID)

    fun updateCartCheckout(personID: String, productID: String): Completable {
        return appDao.updateCartCheckout(personID, productID)
    }

    fun updateCartNumber(quantity: Int, personID: String, productID: String) =
        appDao.updateCartNumber(quantity, personID, productID)

    fun checkoutCart(id: String): Completable {
        return appDao.checkoutCart(id)
    }

    fun deleteCart(personID: String, productID: String): Completable {
        return appDao.deleteItemFromCart(personID, productID)
    }


}