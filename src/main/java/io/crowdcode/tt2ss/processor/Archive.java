package io.crowdcode.tt2ss.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.crowdcode.tt2ss.processor.Library.logger;

/**
 * An archive is a collection of Toontrack Libraries
 */
public class Archive  {

    private final File toontrackSource;
    private final File slateDest;

    public Archive(File toontrackSource, File slateDest) {
        this.toontrackSource = toontrackSource;
        this.slateDest = slateDest;
    }

    /**
     * walk the directories and collect all potential library candidates
     */
    public void processDirectories() {
        File[] files = toontrackSource.listFiles();
        List<Library> libraries = new ArrayList<Library>();
        for (File file : files) {
            Library l = new Library(file, slateDest);
            if (l.isToontrackArchive()) {
                libraries.add(l);
            } else {
                logger.warning("NOT AN ARCHIVE. SKIPPED "+file.getAbsolutePath());
            }
        }

        List<Library> validLibraries = new ArrayList<Library>();
        for (Library library : libraries) {
            if (library.checkOutputDirectory()) {
                validLibraries.add(library);
            } else {
                logger.warning("SKIPPED "+library);
            }
        }

        logger.info("PROCESSING ALL VALID LIBS");
        int count=1;
        for (Library validLibrary : validLibraries) {
            logger.info("PROCESSING LIBRARY "+count+++" OF "+validLibraries.size());
            validLibrary.processSections();
        }


    }
}
