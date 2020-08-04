package com.lxd.service.impl;


import com.lxd.mapper.FilePathMapper;
import com.lxd.mapper.ItemMapper;
import com.lxd.pojo.FilePath;
import com.lxd.pojo.Item;
import com.lxd.service.itemSlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private static Logger logger = LoggerFactory.getLogger(itemSlistServiceImpl.class);
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private FilePathMapper filePathMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public List<Item> itemList() {
        //查询redis
        List<Item> sylist = (List<Item>) redisTemplate.opsForValue().get("alllist");
        if(null == sylist){
            logger.info("进");
            sylist = itemMapper.selectAll();
            redisTemplate.opsForValue().set("alllist",sylist);
        }
        return sylist;
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
