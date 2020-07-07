package com.lxd.service.impl;

import com.lxd.mapper.ItemMapper;
import com.lxd.pojo.Item;
import com.lxd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName ItemServiceImpl
 * @Author lxd
 * @Date 2020/7/7 21:59
 * @Description TODO
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void add(Item item) {
        item.setCreateTime(new Date(System.currentTimeMillis()));
        itemMapper.insert(item);
    }
}
