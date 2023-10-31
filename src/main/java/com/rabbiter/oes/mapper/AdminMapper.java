package com.rabbiter.oes.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.oes.entity.Admin;
import com.rabbiter.oes.entity.BpjPerson;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Update("update `userinfo` set passWord = '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92' where userId = #{userId}")
    public int updatePWD1(String userId);

    @Update("update `userinfo` set passWord = '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92' where userUass = #{userUass}")
    public int updatePWD2(String userUass);

    @Select("select * from leaderinfo")
    public List<BpjPerson> selectAll();

    @Select("select * from leaderinfo")
    IPage<BpjPerson> selectAllPage(Page page);

    @Select("select * from leaderinfo where name = #{name}")
    List<BpjPerson> selectAll1(String name);


}
