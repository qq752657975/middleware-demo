package com.ygb.mysqldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ygb.mysqldemo.pojo.TPmUser;
import com.ygb.mysqldemo.pojo.TpmUserDate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2023/1/11 11:41
 */
@Mapper
public interface TPmUserMapper extends BaseMapper<TPmUser> {
    List<TpmUserDate> selectUid();
}
