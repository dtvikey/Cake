package com.imooc.cake.mapper;

import com.imooc.cake.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: dtvikey
 * @Date: 22/11/18 下午 02:49
 * @Version 1.0
 * 分类
 */
public interface CategoryMapper {

    /**
     * 查询全部蛋糕分类
     * @return 全部蛋糕分类
     */
    @Select("select id,name,create_time createTime,update_time updateTime from category")
    List<Category> getCategories();

    /**
     * 根据ID删除某一具体分类
     * @param id 要删除的分类ID
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 插入蛋糕分类信息
     * @param category 蛋糕分类实体
     */
    @Insert("insert into category(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void addCategory(Category category);



}
