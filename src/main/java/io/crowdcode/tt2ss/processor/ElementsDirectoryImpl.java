package io.crowdcode.tt2ss.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static io.crowdcode.tt2ss.utils.StringUtil.capitalizeString;

/**
 * Elements directory are the bottom of the fish tank where the midi gradle is beeing held.
 * (e.g. 000332@DOOM#CORE -> 305@STRAIGHT_4#4 -> 140-S021@INTRO)
 *       (lib)            ->      (sng)       ->     (prt)
 */
public class ElementsDirectoryImpl extends ElementsDirectory {


    private final static List<String> exclusionMarkers= Arrays.asList(new String[] {"header", ".dummy"});


    private final File directory;

    public ElementsDirectoryImpl(File directory) {
        this.directory = directory;
    }

    @Override
    public boolean isValidElementsDirectory() {
        if (!directory.isDirectory()) {
            return false;
        } else {
            for (File file : directory.listFiles()) {
                String name=file.getName();
                if (exclusionMarkers.contains(name)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void processElementFiles(File outputDirectory) {
        File targetDirectory = new File(outputDirectory.getAbsolutePath()+"/"+determineNewName(directory.getName()));
        File[] files = directory.listFiles();

        processFiles(targetDirectory, files);
    }


}
