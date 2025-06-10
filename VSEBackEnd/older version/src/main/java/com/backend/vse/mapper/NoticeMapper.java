package com.backend.vse.mapper;

import com.backend.vse.dto.NoticeDto;
import com.backend.vse.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    /**
     * 根据课程id在notice表中查找该课程下的所有通知信息
     */
    @Select("SELECT notice_id AS noticeId,title,content,time FROM notice WHERE course_id =#{courseId}")
    ArrayList<NoticeDto> selectByCourseId(@Param("courseId") Long courseId);
    /**
     * 向notice表中插入一条新的消息
     * 参数：course_id, title, content, time
     */
    @Insert("INSERT INTO notice(course_id, title, content, time)" +
            "VALUES (#{courseId},#{title},#{url},#{time})")
    boolean newNoticeInsert(@Param("courseId") Long courseId, @Param("title") String title,
                              @Param("url") String content, @Param("time") Timestamp time);
    /**
     * 根据通知的id，在notice表中查找相应的通知，并更新其title、content、time
     */
    @Update("UPDATE notice " +
            "SET title = #{title}, content = #{content} , time = #{time} " +
            "WHERE notice_id = #{notice_id} ")
    void updateNotice(@Param("notice_id") Long noticeId, @Param("time") Timestamp time,
                      @Param("content") String url, @Param("title") String title);
    /**
     * 根据通知id在notice表中查找该通知的信息
     * LIMIT 1限制了只返回一条结果
     */
    @Select("SELECT * FROM notice WHERE notice_id=#{noticeId} LIMIT 1")
    Notice selectByNoticeId(@Param("noticeId") Long noticeId);
    /**
     * 获取最近插入 notice 表的自增主键 ID，即最近插入的通知的id
     * LAST_INSERT_ID() 是一个 MySQL 函数，它返回当前会话中最近插入的自增 ID
     */
    @Select("SELECT LAST_INSERT_ID() from notice ")
    Long getLastNoticeId();
    /**
     * 根据通知id在notice表中删除该通知
     */
    @Delete("DELETE FROM notice WHERE notice_id = #{noticeId}")
    void deleteNotice(@Param("noticeId") Long noticeId);
}
