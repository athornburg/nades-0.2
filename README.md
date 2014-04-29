nades.
======

About
-----

Nades is a peer to peer, real time, location based, game where people can see opponents on a map and detonate a nade when they are in range! Points are scored and linking up with friends is simple!

Building this app
-----------------

This is an android studio application and can be built with the gradle build system. Its easy!

First add a local.properties file in the root directory with a line like this:

```
sdk.dir=/Users/alexthornburg/adt/sdk
```

Next in the command line execute the following commands:

```
cd /your/project/directory
./gradlew build
(if app is already installed) adb -d uninstall io.pacmonitorandroid.app
adb -d install app/build/apk/app-debug-unaligned.apk
```

And there ya go! Nades is on your phone! 
 
