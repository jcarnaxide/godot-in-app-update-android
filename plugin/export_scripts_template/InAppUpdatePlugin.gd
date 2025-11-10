@tool
extends EditorPlugin

const PLUGIN_NODE_TYPE_NAME = "@pluginNodeName@"
const PLUGIN_PARENT_NODE_TYPE = "Node"
const PLUGIN_NAME: String = "@pluginName@"
#const ANDROID_DEPENDENCIES: Array = [ @androidDependencies@ ]

# A class member to hold the editor export plugin during its lifecycle.
var android_export_plugin : AndroidExportPlugin

func _enter_tree():
	add_custom_type(PLUGIN_NODE_TYPE_NAME, PLUGIN_PARENT_NODE_TYPE, preload("%s.gd" % PLUGIN_NODE_TYPE_NAME), preload("icon.png"))
	android_export_plugin = AndroidExportPlugin.new()
	add_export_plugin(android_export_plugin)


func _exit_tree():
	remove_custom_type(PLUGIN_NODE_TYPE_NAME)
	remove_export_plugin(android_export_plugin)
	android_export_plugin = null


class AndroidExportPlugin extends EditorExportPlugin:
	var _plugin_name = PLUGIN_NAME

	func _supports_platform(platform):
		return platform is EditorExportPlatformAndroid

	func _get_android_libraries(platform, debug):
		if debug:
			return PackedStringArray(["%s/bin/debug/%s-debug.aar" % [_plugin_name, _plugin_name]])
		else:
			return PackedStringArray(["%s/bin/release/%s-release.aar" % [_plugin_name, _plugin_name]])

	#func _get_android_dependencies(platform: EditorExportPlatform, debug: bool) -> PackedStringArray:
		#var deps: PackedStringArray = PackedStringArray(ANDROID_DEPENDENCIES)
		#InAppUpdate.log_info("Android dependencies: %s" % str(deps))
		#return deps

	func _get_name():
		return _plugin_name
