package com.zgczx.repository;

import com.zgczx.dataobject.StuBase;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @Author chen
 * @Date 11:12 2018/12/29
 **/

public interface StuBaseRepository extends JpaRepository<StuBase,String> {

    /**
     * 通过学生微信编号获取学生信息
     *
     * @param stuOpenid 学生微信编号
     * @return 学生信息
     */
    public StuBase findByStuOpenid(String stuOpenid);
}
