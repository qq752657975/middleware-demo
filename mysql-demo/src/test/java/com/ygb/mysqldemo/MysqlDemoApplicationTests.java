package com.ygb.mysqldemo;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ygb.mysqldemo.mapper.TPmUserMapper;
import com.ygb.mysqldemo.mapper.UmsMemberMapper;
import com.ygb.mysqldemo.pojo.TPmUser;
import com.ygb.mysqldemo.pojo.TpmUserDate;
import com.ygb.mysqldemo.pojo.UmsMember;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class MysqlDemoApplicationTests {


    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Autowired
    private TPmUserMapper tPmUserMapper;

    @Test
    void test3(){

        //找到老表所有用户
        List<TpmUserDate> list = tPmUserMapper.selectUid();

        //循环老表
        for (TpmUserDate tpmUserDate : list) {

            //获取老表在我们表中的对应手机号用户信息
            UmsMember user =
                    umsMemberMapper.selectPhone(tpmUserDate.getPhoneNumber());

            //判断老表有无上级用户的手机
            if(CharSequenceUtil.isNotBlank(tpmUserDate.getUPhoneNumber()) && ObjectUtil.isNotNull(user)){

                //老表中的上级用户手机号,查询我们数据库对应的手机号用户
                UmsMember tUser =
                        umsMemberMapper.selectPhone(tpmUserDate.getUPhoneNumber());

                //如果存在,修改对应上级信息
                if(ObjectUtil.isNotNull(tUser)){
                    System.out.println("修改用户上级" + user.getMemberPhone());
                    user.setParentId(tUser.getMemberId());
                    user.updateById();
                }
            }
        }


        System.out.println(list);


        //System.out.println(tPmUserMapper.selectList(null));
    }

    @Test
    void test4(){
        //所有老用户
        List<TpmUserDate> list = tPmUserMapper.selectUid();

        long count = 0;
        //循环老表
        for (TpmUserDate tpmUserDate : list) {

            //找老表中的手机,在现表中对应用户
            UmsMember user =
                    umsMemberMapper.selectPhone(tpmUserDate.getPhoneNumber());

            //老表中是否有上级用户,并且我们表中是有有这个用户
            if(CharSequenceUtil.isNotBlank(tpmUserDate.getUPhoneNumber()) && ObjectUtil.isNotNull(user)){

                //老表中的上级手机,找我们库中对应的用户
                UmsMember tUser =
                        umsMemberMapper.selectPhone(tpmUserDate.getUPhoneNumber());
                //判断我们库用户 是否等于这个上级
               if(ObjectUtil.isNotNull(tUser) && ObjectUtil.isNotNull(user.getParentId()) && !user.getParentId().equals(tUser.getMemberId())){
                   System.out.println(user.getMemberPhone() + "上级不正确");
                   count++;
               }
            }
        }
        System.out.println(count);
    }

    @Test
    void test5(){
        List<UmsMember> umsMembers = umsMemberMapper.selectList(null);

        for (UmsMember umsMember : umsMembers) {
            if(ObjectUtil.isNotNull(umsMember.getParentId()) && !umsMember.getParentId().equals(-1L)){
                UmsMember umsMember1 = umsMemberMapper.selectById(umsMember.getParentId());
                if(ObjectUtil.isNotNull(umsMember1) && ObjectUtil.isNotNull(umsMember1.getParentId()) && !umsMember1.getParentId().equals(-1L)){
                    umsMember.setGrandpaId(umsMember1.getParentId());
                    umsMember.updateById();
                }
            }
        }

    }
}
