package io.crowdcode.tt2ss;

import io.crowdcode.tt2ss.preconditions.BasicPreconditions;
import io.crowdcode.tt2ss.processor.Archive;

import java.io.File;
import java.util.logging.Logger;

import static io.crowdcode.tt2ss.processor.ElementsDirectory.alreadyDone;

/**
 * Nifty converter tool moving
 *
 */
public class Toontrack2Slate
{
    static final Logger logger = Logger.getLogger(Toontrack2Slate.class.getSimpleName());

    public static void main( String[] args )
    {
        if (args.length < 2 || args.length > 3){
            System.out.println("ARGUMENTS : <Toontrack Path> <Some empty directory>");
            System.exit(-1);
        }

        File toontrackPath=new File(args[0]);
        File slatePath=new File(args[1]);

        logger.info("Toontrack Source Directory is : "+toontrackPath.getAbsolutePath());
        logger.info("Slate Output Directory is : "+toontrackPath.getAbsolutePath());

        BasicPreconditions preconditions = new BasicPreconditions();
        if (preconditions.checkSlatePath(slatePath) && preconditions.checkToontrackPath(toontrackPath)) {
            Archive archive = new Archive(toontrackPath, slatePath);
            archive.processDirectories();
        } else {
            System.err.println("ABORTING");
        }

        System.out.println("DONE. TRANSFERRED "+alreadyDone.size()+" FILES.");

    }
}
