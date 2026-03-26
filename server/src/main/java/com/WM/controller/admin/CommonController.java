package com.WM.controller.admin;

import com.WM.constant.MessageConstant;
import com.WM.result.Result;
import com.WM.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags="通用接口API")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {
            String originName = file.getOriginalFilename();
            String sufName = originName.substring(originName.lastIndexOf('.'));
            String newName = UUID.randomUUID().toString() + sufName;
            log.info("文件地址：{}", newName);
            String returnPath = aliOssUtil.upload(file.getBytes(), newName);
            return Result.success(returnPath);
        } catch (Exception e) {
            log.info("出现异常：{}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
