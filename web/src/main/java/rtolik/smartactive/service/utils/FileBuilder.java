package rtolik.smartactive.service.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileBuilder {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FileBuilder.class);
    private String nameFilePath = "/resources/file";

    /**
     * @param multipartFile
     * @return base path photo
     */
    public String saveFile(MultipartFile multipartFile) {
        String path;
        path = nameFilePath;
        return saveFile(multipartFile, path);
    }

    private String saveFile(MultipartFile multipartFile, String folder) {
        try {
            String tag = getFileTeg(multipartFile.getOriginalFilename());
            String uuid = UUID.randomUUID().toString();
            folder = String.format("%s/%s.%s", folder, uuid, tag);
            File file = new File(System.getProperty("catalina.home") + folder);
            file.getParentFile().mkdirs();//!correct
            if (!file.exists()) {
                multipartFile.transferTo(file);
                logger.info("----path[" + folder + "]-------create------------");
            } else {

                logger.info("----path[" + folder + "]-------not create------------");
            }
            loggerInfo(multipartFile.getOriginalFilename(), "create file->");
        } catch (IOException e) {
            loggerError(e, "------------path{" + folder + "}-------------------------error file-------------------------------------");
            e.printStackTrace();
        }
        return folder;
    }

    public String getFileTeg(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void loggerError(Object e, String message) {
        logger.info(message);
        logger.error(e);
    }

    private void loggerInfo(String object, String info) {
        logger.info("-------------------------------------" + info + " [" + object + "]-------------------------------------");
    }

}