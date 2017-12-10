package io.crowdcode.tt2ss.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.crowdcode.tt2ss.utils.StringUtil.capitalizeString;

public abstract class ElementsDirectory {
    private static final Logger logger = Logger.getLogger(ElementsDirectoryImpl.class.getSimpleName());

    public abstract boolean isValidElementsDirectory();

    public abstract void processElementFiles(File outputDirectory);
    public static final List<String> alreadyDone = new ArrayList<>();

    protected String determineNewName(String name){
        // C:\Program Files (x86)\Toontrack\EZDrummer\Midi\000332@DOOM#CORE\305@STRAIGHT_4#4\105-S061@INTRO
        if (name.matches("[0-9]*-S[0-9]*@[A-Z_]*")) {
            String[] split1 = name.split("-");
            String bpm = split1[0];
            String[] split2 = name.split("@");
            String part = split2[1];
            part = part.replace("_", " ");
            String newName = bpm+" "+part+".prt";
            return capitalizeString(newName);
        } else if (name.matches("[0-9A-Z]*@[0-9][#][0-9][A-Z_0-9]*")) {
            String[] split1 = name.split("@");
            String number=split1[0];
            String[] split2 = split1[1].split("_");
            String pace=split2[0];
            String id=split2[1];
            String newName = id+" "+pace+" "+ number+".prt";
            newName = newName.replace("_", " ");
            return capitalizeString(newName);
        } else if (name.matches("[0-9A-Z]*@[0-9A-Z_]*")) {
            name = name.replace("_", " ");
            String[] split1 = name.split("@");
            String marker=split1[0];
            String newName = split1[1] +" "+marker+".prt";
            return capitalizeString(newName);
        }
        else {
            return capitalizeString(name)+".prt";
        }
    }


    protected void processFiles(File targetDirectory, File[] files) {
        targetDirectory.mkdirs();

        for (File f : files){
            if (f.getName().endsWith(".mid")) {
                try {
                    File outputFile = new File(targetDirectory.getAbsolutePath()+"/"+f.getName());

                    if (alreadyDone.contains(outputFile.getAbsolutePath())) {
                        logger.warning("ALREADY COPIED "+outputFile.getAbsolutePath());
                    }

                    Files.copy(f.toPath(), outputFile.toPath());
                    alreadyDone.add(outputFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
