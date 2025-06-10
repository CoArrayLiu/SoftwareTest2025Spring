package com.backend.vse.service.impl;

import com.backend.vse.common.ResponseCode;
import com.backend.vse.dto.SupplyAndDemandDto;
import com.backend.vse.entity.SupplyAndDemand;
import com.backend.vse.exception.BusinessException;
import com.backend.vse.exception.DataNotFoundException;
import com.backend.vse.exception.DatabaseOperationException;
import com.backend.vse.mapper.SupplyAndDemandMapper;
import com.backend.vse.mapper.UserMapper;
import com.backend.vse.service.SupplyAndDemandService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor // Lombok 自动生成构造函数
public class SupplyAndDemandServiceImpl implements SupplyAndDemandService {
    private final SupplyAndDemandMapper supplyAndDemandMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public int getQuoteMoneyOfStudent(Long courseId,Long studentIndex) {
        SupplyAndDemand record = Optional.ofNullable(
                supplyAndDemandMapper.selectOne(
                        new LambdaQueryWrapper<SupplyAndDemand>()
                                .eq(SupplyAndDemand::getCourseId, courseId)
                                .eq(SupplyAndDemand::getStudentIndex, studentIndex)
                )
        ).orElseThrow(() -> new DataNotFoundException("该学生在该课程下没有报价记录"));

        return Optional.ofNullable(record.getMoney())
                .orElseThrow(() -> new DataNotFoundException("该学生在该课程下报价金额为空"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplyAndDemandDto> getQuoteAllByCourseId(Long courseId) {
        List<SupplyAndDemand> supplyAndDemands = Optional.ofNullable(
                supplyAndDemandMapper.getQuoteAllByCourseId(courseId)
        ).orElseThrow(() -> new DataNotFoundException("该课程暂无报价记录"));

        List<SupplyAndDemandDto> supplyAndDemandDtos= new ArrayList<>();
        for (SupplyAndDemand supplyAndDemand : supplyAndDemands) {
            String studentId = Optional
                    .ofNullable(userMapper.SelectUserByStudentIndex(supplyAndDemand.getStudentIndex()))
                    .orElseThrow(()->new DataNotFoundException("未找到该课程中学生")).getId();
            supplyAndDemandDtos.add(new SupplyAndDemandDto(studentId,supplyAndDemand.getMoney(),supplyAndDemand.getSubmitTime(),supplyAndDemand.getQuoteType()));
        }
        return supplyAndDemandDtos;
    }

    @Override
    @Transactional
    public void QuoteOfStudent(SupplyAndDemand supplyAndDemand) {
        Integer quoteMoneyOfStudent = supplyAndDemandMapper.getQuoteMoneyOfStudent(supplyAndDemand.getCourseId(), supplyAndDemand.getStudentIndex());
        if(quoteMoneyOfStudent == null){
            try{
                int result = supplyAndDemandMapper.insert(supplyAndDemand);
                if(result <= 0){
                    throw new DatabaseOperationException(
                            ResponseCode.SYSTEM_DB_ERROR,
                            "添加报价失败"
                    );
                }
                log.info("学生 {} 第一次报价成功", supplyAndDemand.getStudentIndex());
            }  catch (DataAccessException e) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR,
                        "添加报价失败"
                );
            }
        }
        else{
            try{
                QueryWrapper<SupplyAndDemand> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("student_index", supplyAndDemand.getStudentIndex());

                int updateCount = supplyAndDemandMapper.update(supplyAndDemand, queryWrapper);
                if(updateCount <= 0){
                    throw new DatabaseOperationException(
                            ResponseCode.SYSTEM_DB_ERROR,
                            "更新报价失败"
                    );
                }
                Optional.of(updateCount)
                        .filter(count -> count == 1)
                        .orElseThrow(() -> new BusinessException(400, "更新报价信息失败"));
                log.info("学生 {} 更新报价成功", supplyAndDemand.getStudentIndex());
            }catch (DataAccessException e) {
                throw new DatabaseOperationException(
                        ResponseCode.SYSTEM_DB_ERROR,
                        "更新报价失败"
                );
            }
        }
    }

    @Override
    @Transactional
    public void deleteQuoteByCourseId(Long courseId) {
        int result = supplyAndDemandMapper.deleteQuoteByCourseId(courseId);
        if(result <= 0){
            throw new DatabaseOperationException(
                    ResponseCode.SYSTEM_DB_ERROR,
                    "删除报价失败"
            );
        }
    }
}