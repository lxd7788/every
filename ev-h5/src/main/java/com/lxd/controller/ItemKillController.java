package com.lxd.controller;

import com.lxd.pojo.ItemKill;
import com.lxd.service.ItemKillService;
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

    @Autowired
    private ItemKillService itemKillService;

    /**
     * 秒杀详情list
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object>  all(){
        List<ItemKill> itemKills = itemKillService.selectList();
        return ResponseEntity.ok(itemKills);
    }

    @PostMapping("/xiadan")
    public ResponseEntity<Object>  xiadan(@RequestParam(value = "id" ,required=false,defaultValue="0") String id){
        ItemKill itemKill = itemKillService.SelectItemKillById(Integer.parseInt(id));
        return ResponseEntity.ok(itemKill);
    }
}
