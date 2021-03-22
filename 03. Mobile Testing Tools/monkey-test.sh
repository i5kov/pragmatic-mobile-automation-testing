#!/bin/bash

#########################################
# About:
# - Monkey test on Android application
#########################################

# Define variables
# To get package name plase use "$ANDROID_HOME/platform-tools/adb shell pm list packages -3"
export APK_PATH=Android-NativeDemoApp-0.2.1.apk
export PACKAGE_ID=com.wdiodemoapp
export EMULATOR_NAME=Pixel4Api30

# Stop running emulators
$ANDROID_HOME/platform-tools/adb devices | grep emulator | cut -f1 | while read line; do $ANDROID_HOME/platform-tools/adb -s $line emu kill  > /dev/null 2>&1; done
sleep 2

# Start Emulator
echo "Start emulator ..."
$ANDROID_HOME/emulator/emulator @$EMULATOR_NAME -wipe-data > /dev/null 2>&1 &

# Wait until it boots
echo "Wait until emulator boot ..."
$ANDROID_HOME/platform-tools/adb -e wait-for-device

BOOT_STATUS=$($ANDROID_HOME/platform-tools/adb -e shell getprop sys.boot_completed | tr -d '\r')
while [ "$BOOT_STATUS" != "1" ]; do
  echo "Wait boot animations ..."
  sleep 2
  BOOT_STATUS=$($ANDROID_HOME/platform-tools/adb -e shell getprop sys.boot_completed | tr -d '\r')
done

# Install the app under test
echo "Install application under test ..."
$ANDROID_HOME/platform-tools/adb -e install -r $APK_PATH > /dev/null 2>&1

# Clear device logs
echo "Clean device logs ..."
$ANDROID_HOME/platform-tools/adb -e logcat -c > /dev/null 2>&1

# Start monkey test
$ANDROID_HOME/platform-tools/adb shell monkey -p com.wdiodemoapp -s 25 -v 1000 --throttle 250 --pct-syskeys 0 --pct-anyevent 0