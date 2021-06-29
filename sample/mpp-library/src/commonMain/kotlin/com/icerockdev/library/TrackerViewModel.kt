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
    private val _textLocation: MutableLiveData<String> = MutableLiveData("no data")
    val textLocation: LiveData<String> = _textLocation.readOnly()

    private val _textExtendedLocation: MutableLiveData<String> = MutableLiveData("no data")
    val textExtendedLocation: LiveData<String> = _textExtendedLocation.readOnly()

    init {
        viewModelScope.launch {
            locationTracker.getLocationsFlow()
                .distinctUntilChanged()
                .collect { _textLocation.value = it.toString() }
        }

        viewModelScope.launch {
            locationTracker.getExtendedLocationsFlow()
                .distinctUntilChanged()
                .collect {
                    _textExtendedLocation.value = """
                        locationAccuracy=${it.location.coordinatesAccuracyMeters}
                        
                        ${it.altitude}
                        
                        ${it.azimuth}
                        
                        ${it.speed}
                        
                        timestamp=${it.timestampMs}
                    """.trimIndent()
                }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    fun onStartPressed() {
        viewModelScope.launch {
            try {
                locationTracker.startTracking()
            } catch (exc: Throwable) {
                _textLocation.value = exc.toString()
                _textExtendedLocation.value = exc.toString()
            }
        }
    }

    fun onStopPressed() {
        locationTracker.stopTracking()
    }
}
