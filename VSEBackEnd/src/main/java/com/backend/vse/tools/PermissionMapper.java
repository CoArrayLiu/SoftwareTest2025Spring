package com.backend.vse.tools;

import com.backend.vse.tools.Enums.Activity;
import com.backend.vse.tools.Enums.TeachingActivity;
import com.backend.vse.tools.Enums.UserAndAccountActivity;

import java.util.*;

public class PermissionMapper {
    // 使用泛型Map存储所有权限
    private static final Map<String, Map<Class<? extends Activity>, Set<? extends Activity>>> PERMISSIONS = new HashMap<>();

    static {
        // 初始化权限映射
        Map<Class<? extends Activity>, Set<? extends Activity>> studentPermissions = new HashMap<>();
        studentPermissions.put(TeachingActivity.class, EnumSet.of(
                TeachingActivity.PARTICIPATE_PROJECT,
                TeachingActivity.STUDENT_QUOTE,
                TeachingActivity.STUDENT_COURSE_INFO,
                TeachingActivity.STUDENT_EXPERIMENT_INFO,
                TeachingActivity.STUDENT_REPORT
        ));
        studentPermissions.put(UserAndAccountActivity.class, EnumSet.of(
                UserAndAccountActivity.EDIT_ACCOUNT_INFO
        ));
        PERMISSIONS.put("student", studentPermissions);

        Map<Class<? extends Activity>, Set<? extends Activity>> teacherPermissions = new HashMap<>();
        teacherPermissions.put(TeachingActivity.class, EnumSet.of(
                TeachingActivity.PUBLISH_PROJECT,
                TeachingActivity.PARTICIPATE_PROJECT,
                TeachingActivity.GRADE_ASSIGNMENT,
                TeachingActivity.TEACHER_MANAGE_QUOTE,
                TeachingActivity.TEACHER_COURSE_INFO,
                TeachingActivity.MANAGE_COURSE_EXPERIMENT,
                TeachingActivity.TEACHER_EXPERIMENT_INFO,
                TeachingActivity.TEACHER_REPORT,
                TeachingActivity.MANAGE_COURSE_STUDENT
        ));
        teacherPermissions.put(UserAndAccountActivity.class, EnumSet.of(
                UserAndAccountActivity.EDIT_ACCOUNT_INFO
        ));
        PERMISSIONS.put("teacher", teacherPermissions);

        Map<Class<? extends Activity>, Set<? extends Activity>> responsibleTeacherPermissions = new HashMap<>();
        responsibleTeacherPermissions.put(TeachingActivity.class, EnumSet.of(
                TeachingActivity.MANAGE_COURSE,
                TeachingActivity.PUBLISH_PROJECT,
                TeachingActivity.GRADE_ASSIGNMENT,
                TeachingActivity.MANAGE_GRADE,
                TeachingActivity.PARTICIPATE_PROJECT,
                TeachingActivity.TEACHER_MANAGE_QUOTE,
                TeachingActivity.TEACHER_COURSE_INFO,
                TeachingActivity.MANAGE_COURSE_EXPERIMENT,
                TeachingActivity.TEACHER_EXPERIMENT_INFO,
                TeachingActivity.TEACHER_REPORT,
                TeachingActivity.MANAGE_COURSE_STUDENT
        ));
        responsibleTeacherPermissions.put(UserAndAccountActivity.class, EnumSet.of(
                UserAndAccountActivity.EDIT_ACCOUNT_INFO
        ));
        PERMISSIONS.put("responsible_teacher", responsibleTeacherPermissions);

        Map<Class<? extends Activity>, Set<? extends Activity>> systemManagerPermissions = new HashMap<>();
        systemManagerPermissions.put(UserAndAccountActivity.class, EnumSet.of(
                UserAndAccountActivity.CREATE_ACCOUNT,
                UserAndAccountActivity.DELETE_ACCOUNT,
                UserAndAccountActivity.VERIFY_NEW_USER,
                UserAndAccountActivity.ACTIVATE_ACCOUNT,
                UserAndAccountActivity.ASSIGN_ACCOUNT_PERMISSION,
                UserAndAccountActivity.EDIT_ACCOUNT_INFO
        ));
        PERMISSIONS.put("system_manager", systemManagerPermissions);
    }

    // 权限检查
    public static boolean hasPermission(String role, Activity activity) {
        if (role == null || activity == null) {
            return false;
        }

        Map<Class<? extends Activity>, Set<? extends Activity>> rolePermissions = PERMISSIONS.get(role.toLowerCase());
        if (rolePermissions == null) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Set<Activity> allowedActivities = (Set<Activity>) rolePermissions.get(activity.getClass());
        return allowedActivities != null && allowedActivities.contains(activity);
    }
}