// TODO: Update to match your plugin's package name.
package org.godotengine.plugin.android.inappupdate

import android.util.Log
import android.widget.Toast
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot

class InAppUpdatePlugin(godot: Godot): GodotPlugin(godot) {

    companion object {
        const val CLASS_NAME: String = "InAppUpdatePlugin"
        private const val LOG_TAG = "godot::$CLASS_NAME"
    }

    override fun getPluginName(): String = CLASS_NAME

    @UsedByGodot
    fun hello_world() {
        Log.d(LOG_TAG, "hello_world()")
        runOnUiThread {
            Toast.makeText(activity, "Hello World", Toast.LENGTH_LONG).show()
            Log.v(pluginName, "Hello World")
        }
    }
}
