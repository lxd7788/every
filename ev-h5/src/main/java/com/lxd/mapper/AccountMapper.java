package com.lxd.mapper;

import com.lxd.pojo.Account;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

/**
 * 悲观锁，乐观锁 https://www.cnblogs.com/cloudfloating/p/11461530.html
 */
@org.apache.ibatis.annotations.Mapper
public interface AccountMapper extends Mapper<Account> {

    @Update("update account set deposit=#{deposit}, version = version + 1 where id = #{id} and version = #{version}")
    int updateDeposit(Account account);

    @Select("select * from account where id = #{id} for update")
    Account selectByIdForUpdate(int id);

    @Update(" update account set deposit=#{deposit} where id = #{id}")
    void updateDepositPessimistic(Account account);

    @Select("select sum(deposit) from account")
    BigDecimal getTotalDeposit();
}

