package com.backend.vse.interceptor;


import com.backend.vse.tools.Enums.PermissionType;
import com.backend.vse.tools.Enums.TeachingActivity;
import com.backend.vse.tools.Enums.UserAndAccountActivity;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {

    TeachingActivity teaching() default TeachingActivity.PARTICIPATE_PROJECT;
    UserAndAccountActivity account() default UserAndAccountActivity.EDIT_ACCOUNT_INFO;
    PermissionType type();
}
