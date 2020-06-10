LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := MXComponent
LOCAL_SRC_FILES	:= libMXComponent.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := MXComponentHost
LOCAL_SRC_FILES := libMXComponentHost.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Qt5Core
LOCAL_SRC_FILES := libQt5Core.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Qt5Widgets
LOCAL_SRC_FILES := libQt5Widgets.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Qt5Gui
LOCAL_SRC_FILES := libQt5Gui.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Qt5Xml
LOCAL_SRC_FILES := libQt5Xml.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Qt5Network
LOCAL_SRC_FILES := libQt5Network.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := gnustl_shared
LOCAL_SRC_FILES := libgnustl_shared.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := Support
LOCAL_SRC_FILES := libSupport.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := interface
LOCAL_SRC_FILES := libinterface.so
include $(PREBUILT_SHARED_LIBRARY)
