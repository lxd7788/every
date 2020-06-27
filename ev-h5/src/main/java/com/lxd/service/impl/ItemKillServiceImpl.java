package com.lxd.service.impl;

import com.lxd.mapper.ItemKillMapper;
import com.lxd.pojo.ItemKill;
import com.lxd.service.ItemKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ItemKillServiceImpl
 * @Author lxd
 * @Date 2020/6/7 21:50
 * @Description TODO
 */
@Service
public class ItemKillServiceImpl implements ItemKillService {

    @Autowired
    private ItemKillMapper itemKillMapper;
    @Override
    public List<ItemKill> selectList() {
        return itemKillMapper.selectList();
    }

    @Override
    public ItemKill SelectItemKillById(int id) {
        return itemKillMapper.selectByPrimaryKey(id);
    }
}
