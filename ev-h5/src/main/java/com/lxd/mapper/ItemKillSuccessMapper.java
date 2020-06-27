package com.lxd.mapper;

import com.lxd.pojo.ItemKillSuccess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface ItemKillSuccessMapper extends Mapper<ItemKillSuccess> {
    @Select("  SELECT\n" +
            "        COUNT(1) AS total\n" +
            "    FROM\n" +
            "        item_kill_success\n" +
            "    WHERE\n" +
            "        user_id = #{userId}\n" +
            "    AND kill_id = #{killId}\n" +
            "    AND status in (0,1)")
    int countByKillUserId(@Param("killId") Integer killId, @Param("userId") long userId);
}
