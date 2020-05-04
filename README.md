What is this for?
-----------------
I developed this Android application as part of the interview process for Sealing Technologies, Inc. It shows you the current time at your location, plus the time in Honolulu, Los Angeles, Santiago, London, Cario, Moscow, Hong Kong, Sydney, and Tokyo.


How to build
------------
To build the app on Linux, MacOS, or Windows, follow the steps below


Prereqisites
------------
1. A Git client


Downloading the Source Code
---------------------------
1. Navigate to the directory where you want the files to be stored (I recommend `/AndroidStudioProjects` if you have Android Studio installed).

2. Open a terminal/Command Prompt/PowerShell window in this directory and run this command:
```
git clone https://github.com/jmtrotz/WhatTimeIsIt
```

Compiling the Code
------------------
1. For Linux/MacOS users, `cd` into `/whatTimeIsIt` and execute the following command:
```
./gradlew assemble
```

Don't forget the `./` at the beginning!!!


2. For Windows users, `cd` into `\whatTmeIsIt` and execute the following command:
```
gradlew assemble
```

Finished Product
----------------
After it has finished compiling, the APK file will be located in `~/whatimeIsIt/app/build/outputs/apk/`. You have the option to choose between a debug version of the application, or an unsigned release version of the application.
