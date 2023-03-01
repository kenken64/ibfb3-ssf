package sg.edu.nus.iss.app.workshop13.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IOUtil {
    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    /** 
     * Helper method to create directory on the local machine
     */
    public static void createDir(String path){
        File dir = new File(path);
        boolean isDirExist = dir.mkdir();
        logger.info("Is Dir exist ?" + isDirExist);
        if(isDirExist) {
            String osName = System.getProperty("os.name");
            if(!osName.contains("Windows")){
                String permission = "rwxrwx---";
                Set<PosixFilePermission>  permissions = 
                                PosixFilePermissions.fromString(permission);
                try {
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
        }
    }
}
