package io.crowdcode.tt2ss.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.crowdcode.tt2ss.utils.StringUtil.capitalizeString;

/**
 * a library section is a subdirectory of a library. this is, where the elements directories are located.
 * (e.g. 000332@DOOM#CORE -> 305@STRAIGHT_4#4)
 *
 */
public class LibrarySection {

    public static final Logger logger = Logger.getLogger(LibrarySection.class.getSimpleName());
    public static final String PREFIX = "[0-9]*@[_]?";

    private final File section;

    private File outputDirectory;

    private List<ElementsDirectory> elementsDirectories = new ArrayList<ElementsDirectory>();

    public LibrarySection(File section) {
        this.section = section;
        boolean doubleCheck=false;
        for (File file : section.listFiles()) {
            if (file.isDirectory()) {
                ElementsDirectory e = new ElementsDirectoryImpl(file);
                if (e.isValidElementsDirectory()) {
                    elementsDirectories.add(e);
                } else {
                    doubleCheck=true;
                    logger.info("Excluded directory "+file.getAbsolutePath());
                }

            }
        }
        if (doubleCheck || elementsDirectories.size()==0) {
            ElementsDirectory specialCase = checkForClaustrophicSpecialCase();
            if (specialCase != null) {
                elementsDirectories.add(specialCase);
            }
        }
    }

    private ElementsDirectory checkForClaustrophicSpecialCase(){
        // Special case: Section contains midi files, subdirectory exclusion file

        List<File> midis = new ArrayList<>();
        List<File> dir = new ArrayList<>();

        for (File f: section.listFiles()) {
            if (f.getName().endsWith(".mid")) {
                midis.add(f);
            }
            if (f.isDirectory()) {
                if (!new ElementsDirectoryImpl(f).isValidElementsDirectory()) {
                    dir.add(f);
                }
            }
        }

        if(midis.size() > 0&& dir.size()==1){
            return new ClaustrophobicSpecialCaseImpl(dir.iterator().next(), midis);
        } else if (midis.size() > 0&& dir.size()==0) {
            // another special case : no subdir, so we create one named like the parent
            File dest = new File((section.getAbsolutePath())+"/"+section.getName());
            return new ClaustrophobicSpecialCaseImpl(dest, midis);
        } else {
            return null;
        }
    }

    private String determineOutputName(){
        String name = section.getName();
        String[] split = name.split("@");
        String newName = name.replaceFirst(PREFIX, "");
        newName = newName.replace("_", " ");
        newName = newName.replace("@", "");
        if (split != null && split.length > 0) {
            if (split[0].matches("[0-9]*")) {
                newName = newName+ " "+ split[0];
            }
        }

        newName = capitalizeString(newName);

        return newName+".sng";
    }

    public void processSection(File destinationFolder) {
        outputDirectory = new File(destinationFolder.getAbsolutePath()+"/"+determineOutputName());
        logger.info("OUTPUT DIRECTORY IS "+outputDirectory.getAbsolutePath());
        outputDirectory.mkdirs();

        for (ElementsDirectory elementsDirectory : elementsDirectories) {
            if (elementsDirectory.isValidElementsDirectory()) {
                elementsDirectory.processElementFiles(outputDirectory);
            }
        }

    }

}
