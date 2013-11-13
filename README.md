SilentZoneUiTests
=================

Some nice little tests for the SilentZone app: https://play.google.com/store/apps/details?id=ca.silentzone.silentzone


Test Suites
===========

**Basic Tests**
Simple tests that ensure that the application and device are working properly. If these tests fail, something is very wrong
  with the application or the device.
* Open App:
  * opens the application from the app drawer (assuming you're using a stock launcher) or via an am start commnad
* Buttons test:
  * ensures that when in the main activity, one is able to press all of the action bar buttons (play/pause, refresh, add, and options)
  * ensures that after each press, application is in a known state and is able to get back to the main activity
  * 

*These test suites are not yet implemented. They are all ideas of possible test suites and may never be implemented*

**Zone Tests**
More advanced tests that manipulate (add, delete, modify) a "silent zone"

**Multiple Zone Test**
Advanced tests that involve manipulating multiple zones

**Current Zone Tests**
Adds a the zone that the device is currently in, and then modifies what the device should do when it is in this zone.

**Monkey Test**
Performs a Monkey test on the Silentzone application to ensure that it can handle random inputs for long periods of time.
