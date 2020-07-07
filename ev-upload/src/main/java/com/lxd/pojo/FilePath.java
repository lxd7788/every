package com.lxd.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName FilePath
 * @Author lxd
 * @Date 2020/7/7 21:57
 * @Description TODO
 */
@Table(name = "file_path")
public class FilePath {
    @Id
    private Integer id;

    private Integer itemId;

    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
