/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class TrackerViewModel(
    val locationTracker: LocationTracker
) : ViewModel() {
    private val _text: MutableLiveData<String> = MutableLiveData("no data")
    val text: LiveData<String> = _text.readOnly()

    init {
        viewModelScope.launch {
            locationTracker.getLocationsFlow()
                .distinctUntilChanged()
                .collect { _text.value = it.toString() }
        }
    }

    fun onStartPressed() {
        viewModelScope.launch {
            try {
                locationTracker.startTracking()
            } catch(exc: Throwable) {
                _text.value = exc.toString()
            }
        }
    }

    fun onStopPressed() {
        locationTracker.stopTracking()
    }
}
