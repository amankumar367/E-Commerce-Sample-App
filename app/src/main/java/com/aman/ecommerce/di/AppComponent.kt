package com.aman.ecommerce.di

import android.app.Application
import com.aman.ecommerce.data.repo.ProductRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun  inject(productRepo: ProductRepo)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}