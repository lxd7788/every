package com.lxd.pojo;

import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "account")
public class Account {
    private int id;
    private BigDecimal deposit;
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
