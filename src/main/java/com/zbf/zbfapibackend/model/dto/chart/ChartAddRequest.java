package com.zbf.zbfapibackend.model.dto.chart;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zbf
 */
@Data
public class ChartAddRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1855430294952208250L;

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表数据
     */
    private String chartData;

    /**
     * 图表类型
     */
    private String chartType;
}
