package com.lxd.controller;

import com.lxd.pojo.FilePath;
import com.lxd.pojo.Item;
import com.lxd.service.FilePthService;
import com.lxd.service.ItemService;
import com.lxd.service.UploadService;
import com.lxd.utils.GetNum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @ClassName UploadController
 * @Author lxd
 * @Date 2020/7/6 21:44
 * @Description TODO
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private FilePthService filePthService;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        String url = this.uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(url);
    }



    @PostMapping("/add")
    public ResponseEntity<Item> add(Item item, @RequestParam("path") String path){
        LOGGER.info(item.getName()+"---"+path);
        item.setId(GetNum.num());
        item.setCode(UUID.randomUUID().toString());
        item.setIsActive(1);
        itemService.add(item);
        FilePath filePath = new FilePath();
        filePath.setItemId(item.getId());
        filePath.setId(GetNum.num());
        filePath.setPath(path);
        filePthService.add(filePath);
        return ResponseEntity.ok(item);
    }
}