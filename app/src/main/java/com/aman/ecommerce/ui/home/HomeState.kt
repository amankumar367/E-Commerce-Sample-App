package com.aman.ecommerce.ui.home

data class HomeState (
    var loading: Boolean = false,
    var success: Boolean = false,
    var failure: Boolean = false,
    var message: String? = null,
    var data: Any? = null
)