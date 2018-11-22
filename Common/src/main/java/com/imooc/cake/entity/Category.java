package com.imooc.cake.entity;

import java.util.Date;

/**
 * @Author: dtvikey
 * @Date: 22/11/18 下午 02:50
 * @Version 1.0
 * 分类实体类
 */
public class Category {

    private Long id;
    private String name;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
