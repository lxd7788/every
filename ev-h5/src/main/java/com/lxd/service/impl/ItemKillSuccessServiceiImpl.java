package com.lxd.service.impl;

import com.lxd.mapper.ItemKillSuccessMapper;
import com.lxd.pojo.ItemKillSuccess;
import com.lxd.service.ItemKillSuccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName ItemKillSuccessServiceiImpl
 * @Author lxd
 * @Date 2020/6/9 21:51
 * @Description TODO
 */
@Service
public class ItemKillSuccessServiceiImpl implements ItemKillSuccessService {
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;
    @Override
    public int insertKill(ItemKillSuccess record) {
        int i = itemKillSuccessMapper.insertSelective(record);
        return i;
    }

    @Override
    public int countByKillUserId(Integer killId, long userId) {
        return itemKillSuccessMapper.countByKillUserId(killId,userId);
    }

    @Override
    public ItemKillSuccess selectItemOrder(ItemKillSuccess record) {
        Example example = new Example(ItemKillSuccess.class);
        Example.Criteria criteria = example.createCriteria();


        criteria.andEqualTo("killId",record.getKillId());

        criteria.andEqualTo("userId", record.getUserId());
        criteria.andEqualTo("status", 0);
        List<ItemKillSuccess> itemKillSuccesses = itemKillSuccessMapper.selectByExample(example);
        return itemKillSuccesses.get(0);
    }

    @Override
    public ItemKillSuccess selectByCode(String code) {

        ItemKillSuccess itemKillSuccess = itemKillSuccessMapper.selectByPrimaryKey(code);
        return itemKillSuccess;
    }

    @Override
    public int updateStatus(ItemKillSuccess record) {
        return itemKillSuccessMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<ItemKillSuccess> selectList(Long id) {
        Example example = new Example(ItemKillSuccess.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", id);
        List<ItemKillSuccess> itemKillSuccesses = itemKillSuccessMapper.selectByExample(example);
        return itemKillSuccesses;
    }

    @Override
    public int yodateOrderStatus(String code) {
        ItemKillSuccess itemKillSuccess = itemKillSuccessMapper.selectByPrimaryKey(code);
        if(itemKillSuccess!=null && itemKillSuccess.getStatus() == 0){
            ItemKillSuccess bean = new ItemKillSuccess();
            bean.setCode(code);
            bean.setStatus(-1);
            int i = itemKillSuccessMapper.updateByPrimaryKeySelective(bean);
            return i;
        }
        return 0;
    }
}
