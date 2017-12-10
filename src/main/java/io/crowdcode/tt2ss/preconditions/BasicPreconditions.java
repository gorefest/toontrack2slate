package io.crowdcode.tt2ss.preconditions;

import java.io.File;
import java.util.logging.Logger;

public class BasicPreconditions {
    static final Logger logger = Logger.getLogger(BasicPreconditions.class.getSimpleName());

    private boolean basicChecks(File path) {
        if (!path.exists()) {
            logger.warning(path.getAbsolutePath()+" does not exist");
            return false;
        }

        if (!path.isDirectory()) {
            logger.warning(path.getAbsolutePath()+" is not a directory");
            return false;
        }

        if (!path.canRead()) {
            logger.warning(path.getAbsolutePath()+" cannot be read. lacking privileges?");
            return false;
        }
        return true;
    }

    public boolean checkToontrackPath(File path) {

        if (!basicChecks(path)){
            return false;
        }

        File[] files = path.listFiles();
        if (files==null || files.length == 0) {
            logger.warning(path.getAbsolutePath()+" is empty. source directory must not be empty");
            return false;
        }

        return true;
    }

    public boolean checkSlatePath(File path) {

        if (!basicChecks(path)){
            return false;
        }

        File[] files = path.listFiles();
        if (files!=null && files.length > 0) {
            logger.warning(path.getAbsolutePath()+" is not empty. destination directory must be empty");
            return false;
        }

        if (!path.canWrite()) {
            logger.warning(path.getAbsolutePath()+" cannot be written to. lacking privileges?");
            return false;
        }

        return true;
    }
}
