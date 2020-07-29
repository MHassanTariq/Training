/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsApiSingleton
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
enum class MarsApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {

    //Data variables
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties
    //UI variables
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status
    //Coroutine variables
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        coroutineScope.launch {
            var getPropertiesDeferred = MarsApiSingleton.retrofitService.getProperties(filter.value)
            try {
                _status.value = MarsApiStatus.LOADING
                var listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            }
            catch (e : Exception){
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}
