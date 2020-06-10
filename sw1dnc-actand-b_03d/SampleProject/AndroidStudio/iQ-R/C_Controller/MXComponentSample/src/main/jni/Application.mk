APP_PROJECT_PATH := $(call my-dir)

APP_MODULES += MXComponent
APP_MODULES += MXComponentHost
APP_MODULES += Qt5Core
APP_MODULES += Qt5Widgets
APP_MODULES += Qt5Gui
APP_MODULES += Qt5Xml
APP_MODULES += Qt5Network
APP_MODULES += gnustl_shared
APP_MODULES += Support
APP_MODULES += interface

APP_STL := stlport_static
APP_OPTIM := release

APP_ABI := armeabi armeabi-v7a

APP_BUILD_SCRIPT := $(APP_PROJECT_PATH)/Android.mk
