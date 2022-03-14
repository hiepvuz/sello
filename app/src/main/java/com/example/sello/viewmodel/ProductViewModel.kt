package com.example.sello.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sello.entity.Product
import com.example.sello.repository.FireStoreRepository
import com.example.sello.repository.ProductRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)
    private val fireStoreRepository = FireStoreRepository(application)

    fun getProductFromFireStore() = viewModelScope.launch() {
        val list = fireStoreRepository.getProductFromFireStore()
        list.forEach { product -> productRepository.insertProduct(product) }
    }


    fun getAllProduct() = productRepository.getAllProducts()

    fun getProductsByType(key: String) = productRepository.findProductsByType(key)

    fun findProductByID(key: String) = productRepository.findProductByID(key)

    fun updateStock(quantity: Long, id:String): Disposable? = productRepository.updateStock(quantity,id).subscribeOn(
        Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {}
        .subscribe()

    fun updateProductFireStore(product: Product,stock : Long) = viewModelScope.launch() {
        fireStoreRepository.updateProductToFireStore(product,stock)
    }

}