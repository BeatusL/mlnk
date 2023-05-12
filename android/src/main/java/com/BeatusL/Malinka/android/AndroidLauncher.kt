package com.BeatusL.Malinka.android

import android.os.Bundle
import com.BeatusL.Malinka.Malinka
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/** Launches the Android application.  */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration = AndroidApplicationConfiguration()
        configuration.useAccelerometer = false
        configuration.useCompass = false
        configuration.useGyroscope = false
        configuration.useImmersiveMode = true // Recommended, but not required.
        initialize(Malinka(), configuration)
    }
}
