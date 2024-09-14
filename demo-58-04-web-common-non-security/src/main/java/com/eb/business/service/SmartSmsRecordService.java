package com.eb.business.service;

import com.eb.business.dto.sms.req.SmartSmsQueryReqDto;
import com.eb.business.dto.sms.req.SmartSmsStatisticQueryReqDto;
import com.eb.business.dto.sms.rsp.SmartSmsStatisticRspDto;
import com.eb.mp.mybatis.PageParam;
import com.eb.mp.mybatis.PageResult;
import com.eb.mp.mysql.entity.business.SmartSmsRecordEntity;
import com.eb.mp.mysql.mapper.business.SmartSmsRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmartSmsRecordService {
    private final SmartSmsRecordMapper baseMapper;

    public PageResult<SmartSmsRecordEntity> listPage(
            PageParam pageParam, SmartSmsQueryReqDto queryReqDto) {
        return baseMapper.listPage(pageParam, queryReqDto);
    }

    public PageResult<SmartSmsStatisticRspDto> listPageStatistic(
            PageParam pageParam, SmartSmsStatisticQueryReqDto queryReqDto) {
        long total = baseMapper.listPageStatisticCount(queryReqDto);
        List<SmartSmsStatisticRspDto> dtoList = null;
        if (total > 0) {
            dtoList = baseMapper.listPageStatistic(pageParam.getPageStart(), pageParam.getPageSize(), queryReqDto);
        }

        return new PageResult<>(dtoList, total);
    }
}
