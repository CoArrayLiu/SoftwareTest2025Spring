package com.backend.vse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.backend.vse.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户id查找用户信息
     */
    @Select("SELECT * FROM User WHERE id = #{id}")
    User selectByUserId(@Param("id") String id);


    @Select("SELECT * FROM User WHERE user_index = #{user_index}")
    User selectByUserIndex(@Param("user_index") Long user_index);

    /**
     * 批量插入用户
     */
    int batchInsert(@Param("users") List<User> users);

    /**
     * 更新用户密码
     */
    @Update("UPDATE User SET password = #{password} WHERE email = #{email}")
    Integer updatePassword(@Param("email") String email, @Param("password") String password);

    /**
     * 更新用户状态
     */
    @Update("UPDATE User SET status = #{status} WHERE email = #{email}")
    Integer updateStatus(@Param("email") String email, @Param("status") Byte status);

    /**
     * 更新用户头像
     */
    @Update("UPDATE User SET avatar = #{avatar} WHERE user_index = #{id}")
    void updateUserAvatar(@Param("avatar") String avatar, @Param("id") Long id);

    /**
     * 根据Index查询用户
     */
    @Select("""
            SELECT * FROM User u
            JOIN Student s ON u.user_index = s.user_index
            WHERE s.student_index = #{studentIndex}
            """)
    User SelectUserByStudentIndex(@Param("studentIndex") Long studentIndex);

    /**
     * 更新课程下所有用户状态
     */
    @Update("""
            UPDATE User u
            INNER JOIN (
                SELECT t.user_index
                FROM Teacher t
                INNER JOIN teacher_student_course tsc ON t.teacher_index = tsc.teacher_index
                WHERE tsc.course_id = #{courseId}
                UNION
                SELECT s.user_index
                FROM Student s
                INNER JOIN teacher_student_course tsc ON s.student_index = tsc.student_index
                WHERE tsc.course_id = #{courseId}
            ) temp ON u.user_index = temp.user_index
            SET u.status = #{newStatus}
            WHERE u.status != #{newStatus}
            """)
    int updateStatusByCourseId(@Param("courseId") Long courseId, @Param("newStatus") byte newStatus);

    /**
     * 更新用户名
     */
    @Update("UPDATE User SET name = #{newUsername} " +
            "WHERE user_index = #{userIndex} " +
            "AND (name != #{newUsername} OR name IS NULL)")
    int updateUsername(@Param("userIndex") Long userIndex, @Param("newUsername") String newUsername);

    /**
     * 更新用户头像
     */
    @Update("UPDATE User SET avatar = #{newAvatar} " +
            "WHERE user_index = #{userIndex} " +
            "AND (avatar != #{newAvatar} OR avatar IS NULL)")
    int updateAvatar(@Param("userIndex") Long userIndex, @Param("newAvatar") String newAvatar);

    /**
     * 更新用户邮箱
     */
    @Update("UPDATE User SET email = #{newEmail} " +
            "WHERE user_index = #{userIndex} " +
            "AND (email != #{newEmail} OR email IS NULL)")
    int updateEmail(@Param("userIndex") Long userIndex, @Param("newEmail") String newEmail);

    /**
     * 批量更新用户角色
     */
    int batchUpdateRole(@Param("userIndices") List<Long> userIndices, @Param("newRole") String newRole);

    /**
     * 批量更新用户状态
     */
    int batchUpdateStatus(@Param("userIndices") List<Long> userIndices, @Param("status") byte status);

    /**
     * 获取用户头像URL
     */
    @Select("SELECT avatar FROM User WHERE user_index = #{userIndex}")
    String getUserAvatar(@Param("userIndex") Long userIndex);

    /**
     * 动态更新用户信息
     */
    int updateUser(User user);
}


