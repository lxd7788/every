package com.lxd.service.impl;

import com.lxd.mapper.FilePathMapper;
import com.lxd.pojo.FilePath;
import com.lxd.service.FilePthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FilePthServiceImpl
 * @Author lxd
 * @Date 2020/7/7 22:01
 * @Description TODO
 */
@Service
public class FilePthServiceImpl implements FilePthService {

    @Autowired
    private FilePathMapper filePathMapper;

    @Override
    public void add(FilePath bean) {
        filePathMapper.insert(bean);

    }
}
