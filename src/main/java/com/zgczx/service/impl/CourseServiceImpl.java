package com.zgczx.service.impl;

import com.zgczx.dataobject.*;
import com.zgczx.dto.OnClassUserInfoDTO;
import com.zgczx.enums.CourseEnum;
import com.zgczx.enums.ResultEnum;
import com.zgczx.enums.SubCourseEnum;
import com.zgczx.exception.SdcException;
import com.zgczx.repository.*;
import com.zgczx.service.CourseService;
import com.zgczx.service.TeaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Jason
 * @date 2019/2/20 13:24
 */
@Service
@Slf4j
public class CourseServiceImpl  implements CourseService {

    @Autowired
    private OnlineCourseRepository onlineCourseRepository;

    @Autowired
    private TeaBaseRepository teaBaseRepository;

    @Autowired
    private TeaCourseRepository teaCourseRepository;

    @Autowired
    private SubCourseRepository subCourseRepository;

    @Autowired
    private StuBaseRepository stuBaseRepository;

    @Autowired
    private TeaService teaService;

    @Override
    public OnlineCourse getOnlineCourseGroupId(Integer courseId) {
        //1.判断当前课程是否创建groupId，若有，则返回；若没有，则创建新的群组
        OnlineCourse onlineCourse = onlineCourseRepository.findOne(courseId);
        if(null == onlineCourse){
            OnlineCourse newOnlineCourse = new OnlineCourse();
            newOnlineCourse.setCourseId(courseId);
            // 设定新的群组为courseId
            newOnlineCourse.setGroupId(courseId);
            return onlineCourseRepository.save(newOnlineCourse);
        }else{
            return onlineCourse;
        }
    }

    @Override
    public Boolean onCourse(String openid,Integer courseId) {

        //1.判断课程信息是否正确
        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);
        if(null == teaCourse){
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,"courseId 不存在");
        }


        //2.判断openid为该课程的老师
        TeaBase teaBase = teaBaseRepository.findByTeaOpenid(openid);
        if(teaBase != null){
            //该openid身份是老师，判断是否是该课程的老师
            if(teaBase.getTeaCode().equals(teaCourseRepository.getOne(courseId).getTeaCode()) ){
                return true;
            }
        }
        //3.判断openid为该课程的预约成功的学生
        StuBase stuBase = stuBaseRepository.findByStuOpenid(openid);
        if(null == stuBase){
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,"openid 既不是老师，也不是学生，请检查");
        }

        List<SubCourse> subCourseList = subCourseRepository.
                findByCourseIdAndSubStatus(courseId, SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());
        if(subCourseList.size()>1){
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,"存在大于1的预约成功的学生");
        }else if(subCourseList.size()==0){
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,"不存在预约成功的学生");
        }else{
            SubCourse subCourse = subCourseList.get(0);
            return subCourse.getStuCode().equals(stuBase.getStuCode());
        }

    }

    @Override
    public String getCountDown(Integer courseId) {

        TeaCourse teaCourse = teaCourseRepository.findOne(courseId);

        Date courseEndTime = teaCourse.getCourseEndTime();

        Date now = new Date();

        long countDown = (courseEndTime.getTime() - now.getTime()) / 1000;

        if(countDown > 0){
            // 若倒计时大于0秒，返回倒计时
            return String.valueOf(countDown);
        }else{
            // 若倒计时不大于0秒，返回0秒，表示课程已结束，则结束课程
            TeaCourse teaCourse1 = teaService.finishCourse(courseId);
            if(teaCourse1 == null){
                // 没有修改为课程结束
                teaService.finishCourse(courseId);
            }
            return "end";
        }
    }

    @Override
    public Boolean onCourseEnd(Integer courseId) {

        // 判断课程状态是否为已结束
        return teaCourseRepository.findOne(courseId).getCourseStatus().equals(CourseEnum.COURSE_FINISH.getCode());
    }

    /**
     * 判断课程courserId是否合法
     * @param courserId 课程id
     * @return Boolean
     */
    @Override
    public Boolean legalCourse(Integer courserId) {
        /*查找学生预约的课程信息*/
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        /*如果预约课程不存在，抛出异常*/
        return teaCourse != null;
    }

    @Override
    public OnClassUserInfoDTO getOnClassUserOpenid(Integer courserId) {
        // 1.得到教师的openid
        TeaCourse teaCourse = teaCourseRepository.findOne(courserId);
        String info;
        if(teaCourse == null){
            info = "课程id非法，不存在";
            throw new SdcException(ResultEnum.PARAM_EXCEPTION,info);
        }

        TeaBase teaBase = teaBaseRepository.findOne(teaCourse.getTeaCode());
        String teaOpenid = teaBase.getTeaOpenid();

        // 2.得到学生的openid
        String stuOpenid;
        List<SubCourse> subCourseList = subCourseRepository.findByCourseIdAndSubStatus(courserId,SubCourseEnum.SUB_CANDIDATE_SUCCESS.getCode());

        if(subCourseList.size()>1){
            info = "课程预约成功的学生大于1人，课程id为"+courserId;
            throw new SdcException(ResultEnum.DATABASE_OP_EXCEPTION,info);
        }else if(subCourseList.size()==1){
            StuBase stuBase = stuBaseRepository.findByStuCode(subCourseList.get(0).getStuCode());
            stuOpenid = stuBase.getStuOpenid();
        }else{
            stuOpenid = "";
        }

        OnClassUserInfoDTO onClassUserInfoDTO = new OnClassUserInfoDTO();
        onClassUserInfoDTO.setCourseId(courserId);
        onClassUserInfoDTO.setTeaOpenid(teaOpenid);
        onClassUserInfoDTO.setStuOpenid(stuOpenid);

        return onClassUserInfoDTO;
    }
}
