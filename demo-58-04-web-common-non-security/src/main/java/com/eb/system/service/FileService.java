package com.eb.system.service;

import com.eb.config.base.properties.BaseProperties;
import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final BaseProperties baseProperties;

    @NonNull
    public String uploadFile(MultipartFile sourceFile, int dates, @NonNull String uuidFileName) {
        String rootDir = baseProperties.getFileUpload().getDir();

        String filePath = null;
        try {
            String extension = FilenameUtils.getExtension(sourceFile.getOriginalFilename());
            if (extension == null) {
                extension = "";
            }
            if (!extension.isEmpty()) {
                extension = "." + extension;
            }
            uuidFileName = uuidFileName + extension;
            filePath = String.format("%s%d%c%s", rootDir, dates, File.separatorChar, uuidFileName);
            File dest = new File(filePath);
            dest.getParentFile().mkdirs();
            sourceFile.transferTo(dest);

            return extension;
        } catch (Exception exception) {
            log.error("transferTo failed, filepath: {}", filePath, exception);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }
    }

    public void downloadFile(HttpServletResponse response, int dates, @NonNull String fileName) {
        String rootDir = baseProperties.getFileUpload().getDir();
        String filePath = String.format("%s%d%c%s", rootDir, dates, File.separatorChar, fileName);

        File file = new File(filePath);
        if (!file.exists()) {
            throw ExceptionUtil.business(ErrorCodeConstants.FILE_NOT_EXISTS, filePath);
        }
        response.reset();
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException exception) {
            log.error("write file failed, file path: {}", filePath, exception);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }
    }
}
