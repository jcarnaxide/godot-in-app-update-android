//
// © 2025-present https://github.com/jcarnaxide
//

import java.util.Properties
import java.io.FileInputStream

val commonProperties = Properties().apply {
	load(FileInputStream("${rootDir}/common/config.properties"))
}

extra.apply {
	// Plugin details
	set("pluginNodeName", commonProperties.getProperty("pluginNodeName"))
	set("pluginName", "${get("pluginNodeName")}Plugin")
	set("pluginPackageName", "org.godotengine.plugin.android.inappupdate")
	set("pluginVersion", commonProperties.getProperty("pluginVersion"))
	set("pluginArchive", "${get("pluginName")}-Android-v${get("pluginVersion")}.zip")

	// Godot
	set("godotVersion", commonProperties.getProperty("godotVersion"))
	set("releaseType", commonProperties.getProperty("releaseType"))
	set("godotAarUrl", "https://github.com/godotengine/godot-builds/releases/download/${get("godotVersion")}-${get("releaseType")}/godot-lib.${get("godotVersion")}.${get("releaseType")}.template_release.aar")
	set("godotAarFile", "godot-lib-${get("godotVersion")}.${get("releaseType")}.aar")

	// Demo
	set("demoAddOnsDirectory", "../demo/addons")

	// Godot resources
	set("templateDirectory", "../plugin/export_scripts_template")
}
