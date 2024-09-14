package com.eb.config.base.properties.nested;

import com.eb.rouyi.file.RuoyiFileUtils;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.File;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
public class FileUploadProperties {
    // 上传目录
    @NotBlank
    private String dir;

    public FileUploadProperties setDir(String dir) {
        // 补充上尾巴上的 "/"
        if (!StringUtils.hasText(dir.trim())) {
            return this;
        }

        dir = dir.trim();

        // 文件路径分隔符：/ 或者 \
        String separatorStr = File.separatorChar + "";
        if (dir.equals(separatorStr)) {
            this.dir = dir;
        } else if (dir.endsWith(separatorStr)) {
            this.dir = dir;
        } else {
            this.dir = dir + separatorStr;
        }

        RuoyiFileUtils.UPLOAD_DIR = this.dir;

        return this;
    }

}
