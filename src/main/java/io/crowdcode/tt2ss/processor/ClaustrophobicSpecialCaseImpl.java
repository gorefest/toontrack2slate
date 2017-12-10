package io.crowdcode.tt2ss.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * This is a special deal, the claustrophic kit occured to break up with the simple (lib)->(sng)->(prt) structure, so
 * this subtype was necessary to cope with the problem. the factory function will either choose a single directory or -
 * if no subdirectories available - it will re-use the (sng) name as (prt) name.
 */
public class ClaustrophobicSpecialCaseImpl extends ElementsDirectory{

    private File elementDir;
    private List<File> midis;

    public ClaustrophobicSpecialCaseImpl(File elementDir, List<File> midis) {

        this.elementDir = elementDir;
        this.midis = midis;
    }

    @Override
    public boolean isValidElementsDirectory() {
        return true;
    }


    @Override
    public void processElementFiles(File outputDirectory) {
        File targetDirectory = new File(outputDirectory.getAbsolutePath()+"/"+determineNewName(elementDir.getName()));

        targetDirectory.mkdirs();

        processFiles(targetDirectory, midis.toArray(new File[midis.size()]));
    }
}
