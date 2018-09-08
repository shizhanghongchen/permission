package com.mmall.dao;

import com.mmall.model.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 根据keyword查询用户
     *
     * @param keyword
     * @return
     */
    SysUser findByKeyword(@Param("keyword") String keyword);

    /**
     * 校验用户邮箱是否存在
     *
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    /**
     * 校验用户电话是否存在
     *
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);
}