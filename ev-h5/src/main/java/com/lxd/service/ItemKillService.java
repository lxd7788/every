package com.lxd.service;

import com.lxd.pojo.ItemKill;

import java.util.List;

public interface ItemKillService {
    List<ItemKill> selectList();
    ItemKill SelectItemKillById(int id);
}
