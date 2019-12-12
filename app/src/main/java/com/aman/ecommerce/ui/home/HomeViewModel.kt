package com.aman.ecommerce.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aman.ecommerce.data.repo.ProductRepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel: ViewModel() {

    var observableState: MutableLiveData<HomeState> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable()

    private lateinit var productRepoI: ProductRepoI

    var state = HomeState()
        set(value) {
            field = value
            publishState(value)
        }

    fun setRepository(productRepoI: ProductRepoI) {
        this.productRepoI = productRepoI
    }

    private fun publishState(state: HomeState) {
        Log.d(TAG," >>> Publish State : $state")
        observableState.postValue(state)
    }

    fun getProductList() {
        Log.d(TAG, " >>> Getting product list")

        state = state.copy(loading = true, message = "Loading . . .")
        compositeDisposable.add(
            productRepoI.getProductList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    state = if (it != null) {
                        state.copy(
                            loading = false,
                            success = true,
                            data = it
                        )
                    } else {
                        state.copy(
                            loading = false,
                            failure = true,
                            message = "Empty list came."
                        )
                    }
                }, {
                    state =
                        state.copy(
                            loading = false,
                            failure = true,
                            message = it.localizedMessage
                        )

                    Log.e(TAG, "Error while getting product list", it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable object")
        compositeDisposable.dispose()
    }

    companion object {
        const val TAG = "HomeViewModel"
    }

}