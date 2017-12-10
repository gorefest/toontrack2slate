#Ez Drummer to Slate Drums conversion Tool

##DISCLAIMER

This nifty little tool is public domain. It comes with no warranty. For me it worked perfectly, but I won't take any 
responsibility for its. Therefore it's free ;-)

Read the instructions carefully and DO NOT CONVERT DIRECTLY INTO YOUR SLATE DRUMS LIB!

##REQUIREMENTS

* Java 8 (can be downloaded [here](http://java.oracle.com))
* cmd or shell - this is a command line tool
* Standalone Jar file 

##USAGE

This tool converts entire toon track archives; this means, you will need to define the library root as starting point. 
Since I wanted to move my entire archive, I did not implement a per-lib function. So the usage is command line based and quite straight forward:

``
java -jar toontrack2slate-1.0.0.jar io.crowdcode.tt2ss.Toontrack2Slate <your-toontrack-library> <an Empty output Directory>
`` 

_I recommend not to convert directly into your slate drums library!_

For converting a large number of files you increase the memory

``
java -Xmx512m -jar toontrack2slate-<version>.jar io.crowdcode.tt2ss.Toontrack2Slate <your-toontrack-library> <an Empty output Directory>
`` 

Here 512m means 512MB RAM. You will notice the need for more memory when the processing becomes significantly slower. I used 
this setting for converting ~30000 MIDI files. 

## AFTERMATH
Since you read until here, I assume you converted into a blank directory. Now you simply need to move to that directory containing the converted files to your Slate Drums library.

##CREDITS

Thanks to KdW Mixing + Mastering for their [helpful youtube video](https://www.youtube.com/watch?v=t-PxsaTjegU) for explaining how a conversion can be done. 
It was so annoying that I created this tool; so thanks for the inspiration and the "How-To" :-)