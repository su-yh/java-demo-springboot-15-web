package com.suyh6201.dto;

import lombok.Data;

/**
 * 文件存储位置结果
 *
 * @author suyh
 * @since 2023-12-01
 */
@Data
public class FileStoreLocationDto {
    // 文件保存的绝对路径，如果是windows 则会有盘符。
    // 通过url 编码后的字符串路径
    private String urlEncodePath;
}
