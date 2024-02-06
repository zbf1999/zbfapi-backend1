package com.zbf.zbfapibackend.mapper;

import com.zbf.zbfapibackend.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author zbf
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2024-01-30 22:50:23
* @Entity com.zbf.zbfapibackend.model.entity.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {

    List<Map<String, Object>> queryChartData(String querySql);

}




