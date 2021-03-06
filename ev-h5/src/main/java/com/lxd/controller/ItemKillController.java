package com.lxd.controller;

import com.lxd.pojo.Item;
import com.lxd.pojo.ItemKill;
import com.lxd.service.ItemKillService;
import com.lxd.service.itemSlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ItemKillController
 * @Author lxd
 * @Date 2020/6/7 22:10
 * @Description TODO
 */
@RestController
@RequestMapping("/miaosha")
public class ItemKillController {

    private static Logger logger = LoggerFactory.getLogger(ItemKillController.class);

    @Autowired
    private ItemKillService itemKillService;

    @Autowired
    private itemSlistService itemSlistService;
    /**
     * 秒杀详情list
     * @return
     * 列表信息放到redis中，同时加锁操作，第一个用户请求时从数据库中查询，之后的请求都从redis
     */
    @GetMapping("/list")
    public ResponseEntity<Object>  all(){
        List<ItemKill> itemKills = itemKillService.selectList();
        return ResponseEntity.ok(itemKills);
    }

    /**
     *
     * @param id
     * @return
     * 修改查询逻辑，根据商品id，从list商品详情中循环获取对象 or 直接存商品对象
     */
    @PostMapping("/xiadan")
    public ResponseEntity<Object>  xiadan(@RequestParam(value = "id" ,required=false,defaultValue="0") String id){
        ItemKill itemKill = itemKillService.SelectItemKillById(Integer.parseInt(id));
        return ResponseEntity.ok(itemKill);
    }

    /**
     * 首页商品列表
     * @return
     */
    @GetMapping("/itemList")
    public ResponseEntity<Object>  itemList(){
        logger.info("1111111111");
        List<Item> items = itemSlistService.itemList();
        for (Item bean:items){
            bean.setFilePath(itemSlistService.findBy(bean.getId()).getPath());
        }
        return ResponseEntity.ok(items);
    }

}
