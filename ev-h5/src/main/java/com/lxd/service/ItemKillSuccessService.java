package com.lxd.service;

import com.lxd.pojo.ItemKillSuccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemKillSuccessService {
    int insertKill(ItemKillSuccess record);

    int countByKillUserId(@Param("killId") Integer killId, @Param("userId") long userId);

    ItemKillSuccess selectItemOrder(ItemKillSuccess record);

    ItemKillSuccess selectByCode(String code);

    int updateStatus(ItemKillSuccess record);

    List<ItemKillSuccess> selectList(Long id);

    int yodateOrderStatus(String code);
}
