package com.zbf.zbfapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbf.zbfapibackend.model.entity.Chart;
import com.zbf.zbfapibackend.service.ChartService;
import com.zbf.zbfapibackend.mapper.ChartMapper;
import org.springframework.stereotype.Service;

/**
* @author zbf
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2024-01-30 22:50:23
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




