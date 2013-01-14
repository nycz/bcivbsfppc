bcivbsfppc
==========

Because calling in various bullshit favors punishes public contamination


dafuq is this
-------------
Just a little something for the huge market share that is taken up by Pocket PC 2002. Oh yes.


how to compile/run
------------------
Both commands must be run from the `classes`-directory, containing `ewe.jar` among other things.

Compile:

`javac -classpath JavaEwe.zip;./ [dirname]/[mainfile].java`

Run: (not that the dot is not a typo, it should not be a slash instead)

`ewe [dirname].[mainfile]` 

Useful flags for the run command:
    /p - to simulate a PocketPC (all windows will appear at the top left of the desktop in this mode - do not move them from there.)
    /s - to simulate a Microsoft Smartphone.
    /w <width> - to specify a specific device screen width in pixels.
    /h <height> - to specify a specific device screen height in pixels.
    
Source of the info is the file `SettingUpTheEweSDK.htm` which can be found at
www.ewesoft.com/Downloads/Ewe149-Developer-SDK.zip 

