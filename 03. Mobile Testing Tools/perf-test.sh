#!/bin/bash

#########################################
# About:
# - Script to get startup time of Android application
# - Please attach real device before run the script
#########################################

# Define variables
# To get package name plase use "$ANDROID_HOME/platform-tools/adb shell pm list packages -3"
export APK_PATH=Android-NativeDemoApp-0.2.1.apk
export PACKAGE_ID=com.wdiodemoapp

# Install the app under test
$ANDROID_HOME/platform-tools/adb -d install -r $APK_PATH > /dev/null 2>&1

# Clear device logs
$ANDROID_HOME/platform-tools/adb logcat -c > /dev/null 2>&1

# Start and stop app under test 3 times
for run in {1..3}; do
  $ANDROID_HOME/platform-tools/adb shell monkey -p $PACKAGE_ID -c android.intent.category.LAUNCHER 1 > /dev/null 2>&1
  sleep 10
  $ANDROID_HOME/platform-tools/adb shell am force-stop $PACKAGE_ID > /dev/null 2>&1
  sleep 10
done

# Print logs
$ANDROID_HOME/platform-tools/adb logcat -d | grep Displayed | grep $PACKAGE_ID