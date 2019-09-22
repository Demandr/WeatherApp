package com.oleksandr.demsky.weatherapp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.oleksandr.demsky.weatherapp.db.AppDatabase
import com.oleksandr.demsky.weatherapp.network.WeatherEndpoint

class Injector(
    private val repository: WeatherEndpoint,
    private val db: AppDatabase
) : FragmentManager.FragmentLifecycleCallbacks(),
    Application.ActivityLifecycleCallbacks {

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
//        super.onFragmentPreCreated(fm, f, savedInstanceState)
        (f as? Injection)?.injectRepository(repository)
        (f as? Injection)?.injectDatabase(db)
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        (activity as? Injection)?.injectRepository(repository)
        (activity as? FragmentActivity)?.supportFragmentManager?.registerFragmentLifecycleCallbacks(this, true)
    }
}