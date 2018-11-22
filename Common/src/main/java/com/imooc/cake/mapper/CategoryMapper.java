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

    @Select("select id,name,create_time createTime,update_time updateTime from category")
    List<Category> getCategories();

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    @Insert("insert into category(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void addCategory(Category category);



}
