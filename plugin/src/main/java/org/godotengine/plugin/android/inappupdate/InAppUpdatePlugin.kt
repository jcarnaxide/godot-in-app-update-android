// TODO: Update to match your plugin's package name.
package org.godotengine.plugin.android.inappupdate

import android.app.Activity
import android.view.ViewGroup
import android.util.Log
import android.widget.Toast
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot
import org.godotengine.godot.plugin.SignalInfo;
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

class InAppUpdatePlugin(godot: Godot): GodotPlugin(godot) {

    companion object {
        const val CLASS_NAME: String = "InAppUpdatePlugin"
        private const val LOG_TAG = "godot::$CLASS_NAME"

        const val SIGNAL_SUCCESS_APP_UPDATE_AVAILABLE = "app_update_available"
        const val SIGNAL_SUCCESS_APP_UPDATE_NOT_AVAILABLE = "app_update_not_available"
        const val SIGNAL_FAILURE_GETTING_UPDATE = "failure_getting_update"
    }

    override fun getPluginName(): String = CLASS_NAME

    private var appUpdateManager: AppUpdateManager? = null
    private var successListener: OnSuccessListener<AppUpdateInfo>? = null
    private var failureListener: OnFailureListener? = null

    override fun getPluginSignals(): MutableSet<SignalInfo> = mutableSetOf(
        SignalInfo(SIGNAL_SUCCESS_APP_UPDATE_AVAILABLE),
        SignalInfo(SIGNAL_SUCCESS_APP_UPDATE_NOT_AVAILABLE),
        SignalInfo(SIGNAL_FAILURE_GETTING_UPDATE)
    )

    @UsedByGodot
    fun start(context: Activity, rootView: ViewGroup) {
        appUpdateManager = AppUpdateManagerFactory.create(context).also { appUpdateManager ->
            successListener = successListener(context, rootView, appUpdateManager)
            failureListener = failureListener()

            appUpdateManager.appUpdateInfo.addOnSuccessListener(requireNotNull(successListener))
            appUpdateManager.appUpdateInfo.addOnFailureListener(requireNotNull(failureListener))
        }
    }

    private fun successListener(
        context: Activity,
        rootView: ViewGroup,
        appUpdateManager: AppUpdateManager
    ) = OnSuccessListener<AppUpdateInfo> { appUpdateInfo ->
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            Log.d(pluginName, "Success: Update Available")
            emitSignal(SIGNAL_SUCCESS_APP_UPDATE_AVAILABLE)
        } else {
            Log.d(pluginName, "Success: Update Not Available")
            emitSignal(SIGNAL_SUCCESS_APP_UPDATE_NOT_AVAILABLE)
        }
    }

    private fun failureListener() = OnFailureListener { error ->
        Log.e(pluginName, String.format("Error: %s", error))
        emitSignal(SIGNAL_FAILURE_GETTING_UPDATE)
    }
}
