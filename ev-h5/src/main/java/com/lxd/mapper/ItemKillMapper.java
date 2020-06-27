package com.lxd.mapper;

import com.lxd.pojo.ItemKill;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ItemKillMapper extends Mapper<ItemKill> {
    @Select(" SELECT\n" +
            "      a.*,\n" +
            "      b.name AS itemName,\n" +
            "      (\n" +
            "        CASE WHEN (now() BETWEEN a.start_time AND a.end_time AND a.total > 0)\n" +
            "          THEN 1\n" +
            "        ELSE 0\n" +
            "        END\n" +
            "      )      AS canKill\n" +
            "    FROM item_kill AS a LEFT JOIN item AS b ON b.id = a.item_id\n" +
            "    WHERE a.is_active = 1")
    List<ItemKill> selectList();

    //减库存操作 ;
    @Update("    UPDATE item_kill\n" +
            "    SET total = total - 1\n" +
            "    WHERE id = #{killId} AND total>0")
    int updateKillItem(Integer killId);

}
