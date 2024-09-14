package com.eb.rouyi.file;

import com.eb.rouyi.util.RuoyiConstants;
import com.eb.rouyi.util.RuoyiStringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class RuoyiFileUploadUtils
{
    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static String getPathFileName(String uploadDir, String fileName)
    {
        int dirLastIndex = RuoyiFileUtils.UPLOAD_DIR.length() + 1;
        String currentDir = RuoyiStringUtils.substring(uploadDir, dirLastIndex);
        return RuoyiConstants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }
}
