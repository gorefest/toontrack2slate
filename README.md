# Ez Drummer to Slate Drums conversion Tool

* also known as : Toontrack to Slate converter
* also known as : The little bastard i needed to write to make use of the midi pack i purchasedl

## DISCLAIMER

This nifty little tool is _public domain_. It comes with no warranty. For me it worked perfectly, but I won't take any 
responsibility for its. Therefore it's free ;-)

Read the instructions carefully and DO NOT CONVERT DIRECTLY INTO YOUR SLATE DRUMS LIB!

## REQUIREMENTS

* Java 8 (can be downloaded [here](http://java.oracle.com))
* cmd or shell - this is a command line tool
* Standalone Jar file (can be found in the bin directory for those, who do not want to build the jar file)

## USAGE

This tool converts entire toon track archives; this means, you will need to define the library root as starting point. 
Since I wanted to move my entire archive, I did not implement a per-lib function. So the usage is command line based and quite straight forward:

``
java -jar toontrack2slate-<version>.jar io.crowdcode.tt2ss.Toontrack2Slate <your-toontrack-library> <an Empty output Directory>
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

## USING YOUR MIDI FILES
I am also customer of [groovemonkee](https://groovemonkee.com/). I quickly discovered a mapping problem between SLATE and GROOVEMONKEE. 
I also use that mapping file for my toontrack midi tracks - it seems to match, so I added my mapping files (GROOVEMONKEE.iom) to the workspace.

## CREDITS

Thanks to KdW Mixing + Mastering for their [helpful youtube video](https://www.youtube.com/watch?v=t-PxsaTjegU) for explaining how a conversion can be done. 
It was so annoying that I created this tool; so thanks for the inspiration and the "How-To" :-)

