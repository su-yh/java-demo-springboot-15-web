package com.suyh6201.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.File;

/**
 * @author suyh
 * @since 2023-11-28
 */
@ConfigurationProperties(VcsProperties.PREFIX)
@Validated
@Data
public class VcsProperties {
    public static final String PREFIX = "suyh.fs";

    @Valid
    private final FileStoreConfig fileStoreConfig = new FileStoreConfig();

    @Data
    @Validated
    public static class FileStoreConfig {
        /**
         * 上传文件存储在磁盘的位置
         * windows 系统在指定盘符
         *
         */
        @NotEmpty
        private String systemDiskRootLocation;

        /**
         * 确保尾巴上有 "/"
         */
        public void setSystemDiskRootLocation(String systemDiskRootLocation) {
            if (!StringUtils.hasText(systemDiskRootLocation.trim())) {
                return;
            }

            systemDiskRootLocation = systemDiskRootLocation.trim();

            // 文件路径分隔符：/ 或者 \
            String separatorStr = File.separatorChar + "";
            if (systemDiskRootLocation.equals(separatorStr)) {
                this.systemDiskRootLocation = systemDiskRootLocation;
            } else if (systemDiskRootLocation.endsWith(separatorStr)) {
                this.systemDiskRootLocation = systemDiskRootLocation;
            } else {
                this.systemDiskRootLocation = systemDiskRootLocation + separatorStr;
            }
        }
    }
}
