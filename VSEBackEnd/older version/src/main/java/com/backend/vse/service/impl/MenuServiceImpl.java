package com.backend.vse.service.impl;

import com.backend.vse.dto.StudentMenuDto;
import com.backend.vse.entity.Course;
import com.backend.vse.entity.CourseExperiment;
import com.backend.vse.entity.Experiment;
import com.backend.vse.entity.User;
import com.backend.vse.mapper.CourseMapper;
import com.backend.vse.mapper.ExperimentMapper;
import com.backend.vse.mapper.StudentAttendCourseMapper;
import com.backend.vse.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 赵帅涛
 * @date 2023/04/09
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentAttendCourseMapper studentAttendCourseMapper;

    @Autowired
    private ExperimentMapper experimentMapper;

    /**
     * 获取菜单配置型
     */
    @Override
    public HashMap<String, Object> getMenuMeta(String icon, String title) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("icon", icon);
        result.put("title", title);
        result.put("isLink", false);
        result.put("isHide", false);
        result.put("isFull", false);
        result.put("isKeepAlive", true);
        return result;
    }

    @Override
    public ArrayList<StudentMenuDto> buildMenuForStudent(User user) {
        ArrayList<StudentMenuDto> result=new ArrayList<>();
        //根据用户index（数据库id）查找该用户所有实验的信息（主要是id）
        ArrayList<CourseExperiment> course_experiments= studentAttendCourseMapper.getExperimentByIndex(user.getIndex());
        //遍历所有实验
        for (CourseExperiment courseExperiment: course_experiments) {
            //根据实验id获取实验详细信息
            Experiment experiment=experimentMapper.selectExperimentById(courseExperiment.getExperimentId());
            //创建StudentMenuDto类并初始化
            StudentMenuDto studentMenuDto=new StudentMenuDto();
            studentMenuDto.setId(experiment.getExperimentId());
            studentMenuDto.setTitle(experiment.getExperimentName());
            studentMenuDto.setName(experiment.getName());
            studentMenuDto.setKind(experiment.getKind());
            studentMenuDto.setContent(null);
            studentMenuDto.setFile(courseExperiment.getInstructor());
            //向结果中插入该学生所参加实验的详细信息
            result.add(studentMenuDto);
        }
        return result;
    }

    @Override
    public ArrayList<StudentMenuDto> buildWholeMenu() {
        List<Experiment> experiments=experimentMapper.selectAllExperiments();
        ArrayList<StudentMenuDto> result=new ArrayList<>();
        for (Experiment experiment: experiments) {
            StudentMenuDto studentMenuDto=new StudentMenuDto();
            studentMenuDto.setId(experiment.getExperimentId());
            studentMenuDto.setTitle(experiment.getExperimentName());
            studentMenuDto.setName(experiment.getName());
            studentMenuDto.setKind(experiment.getKind());
            studentMenuDto.setContent(null);
            studentMenuDto.setFile(null);
            result.add(studentMenuDto);
        }
        return result;
    }

    // 根据课程id获取对应课程的菜单子项的路由配置信息
    @Override
    public ArrayList<HashMap<String, Object>> getCourseSubmenu(long courseId) {
        String courseIdStr = Long.toString(courseId);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();

        HashMap<String, Object> subHome = new HashMap<>();
        subHome.put("path", String.format("/teaching/%s/index", courseIdStr));
        subHome.put("name", String.format("%s-index", courseIdStr));
        subHome.put("component", "/teaching/class/index");
        subHome.put("meta", getMenuMeta("Menu", "主页"));

        result.add(subHome);

        String[] iconList = {"Notification", "Document", "View", "Goblet", "User"};
        String[] nameList = {"announcement", "experiment", "review", "score", "student"};
        String[] titleList = {"公告", "实验", "评阅", "成绩", "人员"};
        for (int i = 0; i < iconList.length; i++) {
            HashMap<String, Object> item = new HashMap<>();
            item.put("path", String.format("/teaching/%s/%s", courseIdStr, nameList[i]));
            item.put("name", String.format("%s-%s", courseIdStr, nameList[i]));
            item.put("component", String.format("/teaching/class/%s/index", nameList[i]));
            item.put("meta", getMenuMeta(iconList[i], titleList[i]));
            result.add(item);

        }
        return result;
    }

    @Override
    public ArrayList<HashMap<String, Object>> getCourseMenuList(long teacherId) {
        //根据教师的id搜索该教师所教授的所有课程的信息
        List<Course> courses = courseMapper.getTeachingCourseList(teacherId);
        System.out.println("courses = " + courses);
        if (courses.isEmpty()) {
            return null;
        }
        //添加课程菜单的路由
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        for (Course course : courses) {
            HashMap<String, Object> temp = new HashMap<>();
            String courseId = course.getCourseId().toString();
            temp.put("path", String.format("/teaching/%s", courseId));
            temp.put("name", course.getCourseId().toString());
            temp.put("redirect", String.format("/teaching/%s/index", courseId));
            temp.put("meta", getMenuMeta("Edit", course.getCourseName()));
            //添加该课程的子项的路由配置信息，如"主页"，"公告", "实验", "评阅", "成绩", "人员"等
            temp.put("children", getCourseSubmenu(course.getCourseId()));
            result.add(temp);
        }
        return result;
    }
}
