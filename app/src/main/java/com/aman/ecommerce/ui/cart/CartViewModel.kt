package com.aman.ecommerce.ui.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aman.ecommerce.data.repo.ProductRepoI
import com.aman.ecommerce.persistant.AppDatabase
import com.aman.ecommerce.ui.home.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CartViewModel(private val database: AppDatabase): ViewModel() {

    var observableState: MutableLiveData<CartState> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable()

    private lateinit var productRepoI: ProductRepoI

    var state = CartState()
        set(value) {
            field = value
            publishState(value)
        }

    fun setRepository(productRepoI: ProductRepoI) {
        this.productRepoI = productRepoI
    }

    fun getLocalProductList() {
        Log.d(TAG, " >>> Getting local product list")

        state = state.copy(loading = true, message = "Loading . . .")
        compositeDisposable.add(
            productRepoI.getLocalProductList(database)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    state = if (it.isNullOrEmpty().not()) {
                        state.copy(
                            loading = false,
                            success = true,
                            data = it
                        )
                    } else {
                        state.copy(
                            loading = false,
                            failure = true,
                            message = "Local product list is empty"
                        )
                    }
                }, {
                    state =
                        state.copy(
                            loading = false,
                            failure = true,
                            message = it.localizedMessage
                        )

                    Log.e(HomeViewModel.TAG, "Error while getting product list", it)
                })
        )
    }

    private fun publishState(state: CartState) {
        Log.d(TAG," >>> Publish State : $state")
        observableState.postValue(state)
    }


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable object")
        compositeDisposable.dispose()
    }

    companion object {
        const val TAG = "CartViewModel"
    }
}