package com.eb.system.controller;

import com.eb.mvc.authentication.annotation.Permit;
import com.eb.mvc.response.annotation.WrapperResponseAdvice;
import com.eb.mvc.vo.ResponseResult;
import com.eb.system.service.FileService;
import com.eb.util.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Tag(name = "文件系统")
@RestController
@RequestMapping("/system/file")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FileController {
    private final FileService fileService;

    @Operation(summary = "文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult<String> uploadFiles(@RequestParam(value = "file") MultipartFile file) {
        Integer dates = DateUtils.convertToInteger(LocalDate.now());
        String uuidFileName = UUID.randomUUID().toString().replaceAll("-", "");
        String suffix = fileService.uploadFile(file, dates, uuidFileName);
        String res = String.format("/system/file/download/%d/%s", dates, uuidFileName + suffix);
        return ResponseResult.ofSuccess(res);
    }

    // 文件上传之后会把路径写到数据库中，所以这里的WEB API 的路径不能修改。
    @Permit(required = false)
    @Operation(summary = "文件下载")
    @RequestMapping(value = "/download/{dates}/{fileName}", method = RequestMethod.GET)
    @WrapperResponseAdvice(enabled = false)
    public void uploadFiles(
            HttpServletResponse response,
            @PathVariable("dates") Integer dates, @PathVariable("fileName") String fileName) {
        fileService.downloadFile(response, dates, fileName);
    }
}
