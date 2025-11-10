#
# © 2025-present https://github.com/jcarnaxide
#

@tool
class_name InAppUpdate extends Node

const PLUGIN_SINGLETON_NAME: String = "@pluginName@"

signal app_update_available
signal app_update_not_available
signal failure_getting_update

const SIGNAL_SUCCESS_APP_UPDATE_AVAILABLE: String = "app_update_available";
const SIGNAL_SUCCESS_APP_UPDATE_NOT_AVAILABLE: String = "app_update_not_available";
const SIGNAL_FAILURE_GETTING_UPDATE: String = "failure_getting_update";


var _plugin_singleton: Object


func _ready() -> void:
	_update_plugin()


func _notification(a_what: int) -> void:
	if a_what == NOTIFICATION_APPLICATION_RESUMED:
		_update_plugin()


func _update_plugin() -> void:
	if _plugin_singleton == null:
		if Engine.has_singleton(PLUGIN_SINGLETON_NAME):
			_plugin_singleton = Engine.get_singleton(PLUGIN_SINGLETON_NAME)
			_connect_signals()
			_plugin_singleton.start()
		elif not OS.has_feature("editor_hint"):
			InAppUpdate.log_error("%s singleton not found!" % PLUGIN_SINGLETON_NAME)


func _connect_signals() -> void:
	_plugin_singleton.connect(SIGNAL_SUCCESS_APP_UPDATE_AVAILABLE, _on_app_update_available)
	_plugin_singleton.connect(SIGNAL_SUCCESS_APP_UPDATE_NOT_AVAILABLE, _on_app_update_not_available)
	_plugin_singleton.connect(SIGNAL_FAILURE_GETTING_UPDATE, _on_failure_getting_update)


func _on_app_update_available() -> void:
	emit_signal(SIGNAL_SUCCESS_APP_UPDATE_AVAILABLE)


func _on_app_update_not_available() -> void:
	emit_signal(SIGNAL_SUCCESS_APP_UPDATE_NOT_AVAILABLE)


func _on_failure_getting_update() -> void:
	emit_signal(SIGNAL_FAILURE_GETTING_UPDATE)


static func log_error(a_description: String) -> void:
	push_error(a_description)
	printerr("[ERROR] %s" % a_description)


static func log_warn(a_description: String) -> void:
	push_warning(a_description)
	printwarn("[WARN] %s" % a_description)


static func log_info(a_description: String) -> void:
	print_rich("[color=lime]INFO: %s[/color]" % a_description)
	print("[INFO] %s" % a_description)
