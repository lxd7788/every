package com.lxd.service.impl;


import com.lxd.mapper.FilePathMapper;
import com.lxd.mapper.ItemMapper;
import com.lxd.pojo.FilePath;
import com.lxd.pojo.Item;
import com.lxd.service.itemSlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName itemSlistServiceImpl
 * @Author lxd
 * @Date 2020/7/12 16:12
 * @Description TODO
 */
@Service
public class itemSlistServiceImpl implements itemSlistService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private FilePathMapper filePathMapper;

    @Override
    public List<Item> itemList() {

        List<Item> items = itemMapper.selectAll();
        return items;
    }

    @Override
    public FilePath findBy(int id) {
        Example example = new Example(FilePath.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",id);
        List<FilePath> filePaths = filePathMapper.selectByExample(example);
        return filePaths.get(0);
    }
}
