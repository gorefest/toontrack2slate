package io.crowdcode.tt2ss.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static io.crowdcode.tt2ss.utils.StringUtil.capitalizeString;

/**
 * A library is a collection of midi file subdirectories (e.g. 000332@DOOM#CORE).
 */
public class Library {
    static final Logger logger = Logger.getLogger(Library.class.getSimpleName());

    private static final List<String> allowedSubfiles= Arrays.asList(new String[]{"Aversion","kitpieces","midiDB"});

    private List<LibrarySection> sections = new ArrayList<LibrarySection>();

    private final File toontrackSource;
    private final File slateDir;
    private File destinationDir;

    public static final String PREFIX = "[0-9]*@[_]?";
    private final String REGEXP = PREFIX + "([a-zA-Z_#])*";

    public Library(File toontrackSource, File slateDest) {
        this.toontrackSource = toontrackSource;
        this.slateDir = slateDest;
    }

    /**
     * checks if the passed file is a valid directory
     * @param f
     * @return true, if all criteria are mateched
     */
    private boolean seemsToBeValid(File f){
        determineOutput();
        for (File file : f.listFiles()) {
            if (!file.isDirectory()) {
                if (!allowedSubfiles.contains(file.getName())) {
                    logger.warning("BAD SUBFILE FOUND. ARE YOU SURE THIS IS A TOONTRACK DIR? -> " + f.getAbsolutePath());
                    return false;
                }
            } else {
                sections.add(new LibrarySection(file));
            }
        }
        return true;
    }

    /**
     *
     * @return
     */
    public boolean isToontrackArchive() {
        String archiveName=toontrackSource.getName();
        if (archiveName.matches(REGEXP) && seemsToBeValid(toontrackSource)){
            return true;
        }
        return false;
    }


    public void processSections(){
        int i = 1;

        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        for (LibrarySection section : sections) {
            logger.info("PROCESSING "+i+++" OF "+sections.size());
            section.processSection(destinationDir);
        }
    }

    /**
     * @return true, if an output create
     */
    public boolean checkOutputDirectory(){
        if (destinationDir.exists() && destinationDir.isDirectory() && destinationDir.listFiles().length > 0){
            logger.warning(destinationDir.getAbsolutePath()+" IS NOT EMPTY.");
            return false;
        }

        return true;
    }

    private void determineOutput() {
        String archiveName=toontrackSource.getName();
        Pattern pattern = Pattern.compile(PREFIX);
        String newName = archiveName.replaceFirst(PREFIX, "");
        newName=newName.replace("_", " ");
        newName = newName.replace("#","-");
        newName = newName.replace("-"," ");
        newName = capitalizeString(newName);
        newName = newName+".lib";
        logger.fine("OUTPUT ARCHIVE NAME HAS BEEN SET TO "+newName);
        destinationDir = new File(slateDir.getAbsolutePath()+"/"+newName);
    }

    public File getDestinationDir() {
        return destinationDir;
    }
}
