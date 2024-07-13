package com.example.architecturemaster.common.utils.FileHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class FileHandler {
    @Value("${path.root}")
    private String rootPath;

    @Value("${path.img}")
    private String imgFolderPath;

    @Value("${path.video}")
    private String videoFolderPath;

    public String uploadFile(MultipartFile mf) {
        try {
            String contentType = mf.getContentType();

            if (isImage(contentType)) {
                return saveFile(mf, imgFolderPath);
            } else if (isVideo(contentType)) {
                return saveFile(mf, videoFolderPath);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String saveFile(MultipartFile mf, String folderPath) throws IOException {
        String fileName = mf.getOriginalFilename();
        Date d = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String fileNewName = Long.toString(d.getTime()) + new Random().nextInt(8999) + 1000 +
                fileName.substring(fileName.lastIndexOf("."));

        String filePath = rootPath + folderPath + File.separator + File.separator + fileNewName;
        File fileDir = new File(rootPath + folderPath + File.separator );
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(filePath);
        mf.transferTo(file);
        return (folderPath  + File.separator + fileNewName).replace("\\", "/");
    }

    public String convertBase64File(String base64Data) throws IOException {
        String folderPath;
        // 解码Base64字符串
        String[] data = base64Data.split(",");
        String[] type = data[0].split(":")[1].split("/");
        byte[] decodedBytes = Base64.getDecoder().decode(data[1]);
        String fileNewName = UUID.randomUUID().toString()+"."+type[1].split(";")[0];
        if (type[0].equals("image")) folderPath = imgFolderPath;
        else folderPath = videoFolderPath;
        return convertBytesToFile(decodedBytes,folderPath,fileNewName);
    }

    public String convertBytesToFile(byte[] decodedBytes,String folderPath,String fileName) throws IOException {
        String filePath = rootPath+folderPath+File.separator+fileName;
        File fileDir = new File(rootPath + folderPath + File.separator);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        Path path = Paths.get(filePath,fileName);
        Files.write(path, decodedBytes, StandardOpenOption.CREATE);
        return (folderPath + File.separator + fileName).replace("\\", "/");
    }

    private boolean isImage(String contentType) {
        return contentType != null && contentType.startsWith("image");
    }

    private boolean isVideo(String contentType) {
        return contentType != null && contentType.startsWith("video");
    }
}

