package com.backend.vse.service.impl;

import com.backend.vse.tools.Enums.*;
import java.util.EnumSet;

public class PersonelImpl {
    public static Personel CreatePersonel(Roles role){
        return switch (role) {
            case STUDENT -> new Personel(
                    Roles.STUDENT,
                    EnumSet.of(
                            TeachingActivity.PARTICIPATE_PROJECT,
                            TeachingActivity.STUDENT_QUOTE,
                            TeachingActivity.STUDENT_COURSE_INFO,
                            TeachingActivity.STUDENT_EXPERIMENT_INFO,
                            TeachingActivity.STUDENT_REPORT
                    ),
                    EnumSet.of(
                            UserAndAccountActivity.EDIT_ACCOUNT_INFO
                    ));
            case TEACHER -> new Personel(
                    Roles.TEACHER,
                    EnumSet.of(
                            TeachingActivity.PUBLISH_PROJECT,
                            TeachingActivity.GRADE_ASSIGNMENT,
                            TeachingActivity.PARTICIPATE_PROJECT,
                            TeachingActivity.TEACHER_MANAGE_QUOTE,
                            TeachingActivity.TEACHER_COURSE_INFO,
                            TeachingActivity.MANAGE_COURSE_EXPERIMENT,
                            TeachingActivity.TEACHER_EXPERIMENT_INFO,
                            TeachingActivity.TEACHER_REPORT,
                            TeachingActivity.MANAGE_COURSE_STUDENT
                    ),
                    EnumSet.of(
                            UserAndAccountActivity.EDIT_ACCOUNT_INFO
                    ));
            case RESPONSIBLE_TEACHER -> new Personel(
                    Roles.RESPONSIBLE_TEACHER,
                    EnumSet.of(
                            TeachingActivity.MANAGE_COURSE,
                            TeachingActivity.MANAGE_GRADE,
                            TeachingActivity.PUBLISH_PROJECT,
                            TeachingActivity.GRADE_ASSIGNMENT,
                            TeachingActivity.PARTICIPATE_PROJECT,
                            TeachingActivity.TEACHER_MANAGE_QUOTE,
                            TeachingActivity.TEACHER_COURSE_INFO,
                            TeachingActivity.MANAGE_COURSE_EXPERIMENT,
                            TeachingActivity.TEACHER_EXPERIMENT_INFO,
                            TeachingActivity.TEACHER_REPORT,
                            TeachingActivity.MANAGE_COURSE_STUDENT
                    ),
                    EnumSet.of(
                            UserAndAccountActivity.EDIT_ACCOUNT_INFO
                    ));
            case SYSTEM_MANAGER -> new Personel(
                    Roles.SYSTEM_MANAGER,
                    EnumSet.noneOf(TeachingActivity.class),
                    EnumSet.of(
                            UserAndAccountActivity.ACTIVATE_ACCOUNT,
                            UserAndAccountActivity.ASSIGN_ACCOUNT_PERMISSION,
                            UserAndAccountActivity.CREATE_ACCOUNT,
                            UserAndAccountActivity.DELETE_ACCOUNT,
                            UserAndAccountActivity.EDIT_ACCOUNT_INFO,
                            UserAndAccountActivity.VERIFY_NEW_USER
                    ));
        };
    }
}
