extends Node2D

@onready var in_app_update: InAppUpdate = $InAppUpdate

func _on_Button_pressed():
	print("Button pressed")
	in_app_update.hello_world()
