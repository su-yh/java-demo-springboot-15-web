package com.suyh6201.component;

import com.suyh6201.config.properties.VcsProperties;
import com.suyh6201.dto.FileStoreLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author suyh
 * @since 2023-12-01
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VcsFileSystemComponent {
    private final VcsProperties vcsProperties;

    public List<FileStoreLocationDto> filesUpload(MultipartFile[] files) {
        return storeSystemDiskFiles(files);
    }

    private String buildUserDiskDir(long userId) {
        String rootDir = vcsProperties.getFileStoreConfig().getSystemDiskRootLocation();
        return rootDir + userId + File.separatorChar;
    }

    private List<FileStoreLocationDto> storeSystemDiskFiles(MultipartFile[] files) {
        long userId = 111L;
        String fileDirPrefix = buildUserDiskDir(userId);

        File dirFile = new File(fileDirPrefix);
        dirFile.mkdirs();

        List<FileStoreLocationDto> resultList = new ArrayList<>();
        for (MultipartFile sourceFile : files) {
            String originalFilename = sourceFile.getOriginalFilename();

            assert originalFilename != null;
            int index = originalFilename.lastIndexOf(".");
            String fileSuffix = ""; // 文件扩展名
            if (index > 0) {
                fileSuffix = originalFilename.substring(index);
            }

            String fileNameUuid = UUID.randomUUID().toString().replace("-", "");
            String fileFullName = fileNameUuid + fileSuffix;
            String filePath = fileDirPrefix + fileFullName;

            File dest = new File(filePath);
            try {
                sourceFile.transferTo(dest);
            } catch (Exception exception) {
                log.error("transferTo failed, filepath: {}", filePath, exception);
                throw new RuntimeException(filePath);
            }

            try {
                String encodeFilePath = URLEncoder.encode(filePath, StandardCharsets.UTF_8.name());

                FileStoreLocationDto fileStoreLocationDto = new FileStoreLocationDto();
                fileStoreLocationDto.setUrlEncodePath(encodeFilePath);
                resultList.add(fileStoreLocationDto);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return resultList;
    }

    public void downloadSystemDiskFile(String filePath, HttpServletResponse response) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException(filePath);
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
            throw new RuntimeException(filePath);
        }
    }
}
