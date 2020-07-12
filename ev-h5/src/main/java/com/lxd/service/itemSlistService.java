package com.lxd.service;

import com.lxd.pojo.FilePath;
import com.lxd.pojo.Item;

import java.util.List;

/**
 * @ClassName itemSlistService
 * @Author lxd
 * @Date 2020/7/12 16:12
 * @Description TODO
 */
public interface itemSlistService {

    List<Item> itemList();

    FilePath findBy(int id);
}
