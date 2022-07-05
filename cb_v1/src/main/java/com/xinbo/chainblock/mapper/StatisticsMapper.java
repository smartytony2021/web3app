package com.xinbo.chainblock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinbo.chainblock.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tony
 * @date 6/24/22 4:24 下午
 * @desc file desc
 */
@Mapper
public interface StatisticsMapper extends BaseMapper<StatisticsEntity> {

    int insertOrUpdate(@Param("entity") StatisticsEntity entity);

    List<StatisticsEntity> findByUidStr(@Param("date") String date, @Param("childList") List<Integer> childList);
}