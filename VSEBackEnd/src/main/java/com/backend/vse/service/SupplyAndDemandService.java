package com.backend.vse.service;

import com.backend.vse.dto.SupplyAndDemandDto;
import com.backend.vse.entity.SupplyAndDemand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyAndDemandService {
    /**
     * 获取某个学生在某门课程中的报价金额
     * 该方法用于获取某个学生在指定课程中的报价金额。
     *
     * @param courseId 课程 ID（Long 类型）
     * @param studentIndex 学生索引（Long 类型）
     * @return 返回报价金额（int 类型）
     */
    int getQuoteMoneyOfStudent(Long courseId,Long studentIndex);

    /**
     * 获取某门课程下所有学生的报价信息
     * 该方法用于获取某门课程下所有学生的报价信息。
     *
     * @param courseId 课程 ID（Long 类型）
     * @return 返回包含报价信息的 DTO 列表（List<SupplyAndDemandDto> 类型）
     */
    List<SupplyAndDemandDto> getQuoteAllByCourseId(Long courseId);

    /**
     * 学生为某门课程报价
     * 该方法用于学生为某门课程提供报价信息，记录该报价信息。
     *
     * @param supplyAndDemand 包含报价信息的 DTO（SupplyAndDemand 类型）
     */
    void QuoteOfStudent(SupplyAndDemand supplyAndDemand);

    /**
     * 删除某门课程的所有报价信息
     * 该方法用于删除指定课程下所有学生的报价信息。
     *
     * @param courseId 课程 ID（Long 类型）
     */
    void deleteQuoteByCourseId(Long courseId);
}