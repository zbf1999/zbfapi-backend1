package com.zbf.zbfapibackend.controller;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zbf.zbfapibackend.annotation.AuthCheck;
import com.zbf.zbfapibackend.common.BaseResponse;
import com.zbf.zbfapibackend.common.DeleteRequest;
import com.zbf.zbfapibackend.common.ErrorCode;
import com.zbf.zbfapibackend.common.ResultUtils;
import com.zbf.zbfapibackend.constant.CommonConstant;
import com.zbf.zbfapibackend.constant.UserConstant;
import com.zbf.zbfapibackend.exception.BusinessException;
import com.zbf.zbfapibackend.exception.ThrowUtils;
import com.zbf.zbfapibackend.manager.AiManager;
import com.zbf.zbfapibackend.model.dto.chart.*;
import com.zbf.zbfapibackend.model.entity.Chart;
import com.zbf.zbfapibackend.model.vo.BiResponse;
import com.zbf.zbfapibackend.model.vo.UserLoginVo;
import com.zbf.zbfapibackend.service.ChartService;
import com.zbf.zbfapibackend.service.UserService;
import com.zbf.zbfapibackend.utils.ExcelUtils;
import com.zbf.zbfapibackend.utils.SqlUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description: 聊天控制器
 * @author zbf
 */
@RestController
@RequestMapping("/chat")
public class ChartController {
    
    @Resource
    private ChartService chartService;
    
    @Resource
    private UserService userService;
    
    @Resource
    private AiManager aiManager;
    
    @PostMapping("/add")
    public BaseResponse<Long> add(@RequestBody ChartAddRequest chartAddRequest, HttpServletRequest request) {
        //判空
        if (chartAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartAddRequest, chart);
        UserLoginVo loginUser = userService.getLoginUser(request);
        chart.setUserId(loginUser.getId());
        boolean save = chartService.save(chart);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(chart.getId());
    }

    /**
     * 删除接口
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteChart(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        UserLoginVo loginUser = userService.getLoginUser(request);
        Long id = deleteRequest.getId();
        Chart oldChart = chartService.getById(id);
        ThrowUtils.throwIf(oldChart == null, ErrorCode.OPERATION_ERROR);
        if (!oldChart.getUserId().equals(loginUser.getId())) {
            ThrowUtils.throwIf(userService.isNotAdmin(request), ErrorCode.NO_AUTH_ERROR);
        }
        boolean remove = chartService.removeById(id);
        ThrowUtils.throwIf(!remove, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
    
    /**
     * 更新接口
     */
    @RequestMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateChart(@RequestBody ChartUpdateRequest chartUpdateRequest) {
        if (chartUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartUpdateRequest, chart);
        Chart oldChart = chartService.getById(chartUpdateRequest.getId());
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);
        boolean update = chartService.updateById(chart);
        ThrowUtils.throwIf(!update, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
    
    /**
     * 查询接口
     */
    @GetMapping("/get")
    public BaseResponse<Chart> getChart(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        Chart chart = chartService.getById(id);
        ThrowUtils.throwIf(chart == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(chart);
    }
    
    @PostMapping("/list/page")
    public BaseResponse<Page<Chart>> listChartByPage(@RequestBody ChartQueryRequest chartQueryRequest) {
        if (chartQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        ThrowUtils.throwIf(current <= 0 || size <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(current > 20 || size > 20, ErrorCode.PARAMS_ERROR);
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
                getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }
    
    @PostMapping("/my/list/page")
    public BaseResponse<Page<Chart>> listMyChartByPage(@RequestBody ChartQueryRequest chartQueryRequest,
            HttpServletRequest request) {
        if (chartQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        long current = chartQueryRequest.getCurrent();
        long size = chartQueryRequest.getPageSize();
        ThrowUtils.throwIf(current <= 0 || size <= 0, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(current > 20 || size > 20, ErrorCode.PARAMS_ERROR);
        UserLoginVo loginUser = userService.getLoginUser(request);
        chartQueryRequest.setUserId(loginUser.getId());
        Page<Chart> chartPage = chartService.page(new Page<>(current, size),
                getQueryWrapper(chartQueryRequest));
        return ResultUtils.success(chartPage);
    }
    
    @PostMapping("edit")
    public BaseResponse<Boolean> editChart(@RequestBody ChartEditRequest chartEditRequest, HttpServletRequest request) {
        if (chartEditRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        Chart chart = new Chart();
        BeanUtils.copyProperties(chartEditRequest, chart);
        Chart oldChart = chartService.getById(chartEditRequest.getId());
        ThrowUtils.throwIf(oldChart == null, ErrorCode.NOT_FOUND_ERROR);
        UserLoginVo loginUser = userService.getLoginUser(request);
        ThrowUtils.throwIf(!oldChart.getUserId().equals(loginUser.getId()) && userService.isNotAdmin(loginUser), ErrorCode.NO_AUTH_ERROR);
        boolean update = chartService.updateById(chart);
        ThrowUtils.throwIf(!update, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }
    
    @PostMapping("gen")
    public BaseResponse<BiResponse> genChartByAi(@RequestPart("file") MultipartFile multipartFile, GenChartByAiRequest genChartByAiRequest, HttpServletRequest request) {
        if (multipartFile == null || genChartByAiRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        String chartType = genChartByAiRequest.getChartType();
        String goal = genChartByAiRequest.getGoal();
        String name = genChartByAiRequest.getName();
        ThrowUtils.throwIf(StringUtils.isBlank(goal) || StringUtils.isBlank(name), ErrorCode.PARAMS_ERROR);
        long size = multipartFile.getSize();
        String originalFilename = multipartFile.getOriginalFilename();
        ThrowUtils.throwIf(size > CommonConstant.MAX_FILE_SIZE, ErrorCode.PARAMS_ERROR, "文件过大");
        String suffix = FileUtil.getSuffix(originalFilename);
        final List<String> validFileSuffixList = List.of("xlsx");
        ThrowUtils.throwIf(!validFileSuffixList.contains(suffix), ErrorCode.PARAMS_ERROR, "文件格式错误");
        UserLoginVo loginUser = userService.getLoginUser(request);
        
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");
        // 拼接分析目标
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据：").append("\n");
        // 压缩后的数据
        String csvData = ExcelUtils.excelToCsv(multipartFile);
        userInput.append(csvData).append("\n");
        
        long biModelId = CommonConstant.BI_MODEL_ID;
        String result = aiManager.doChat(biModelId, userInput.toString());
        String[] splits = result.split("【【【【【");
        if (splits.length < CommonConstant.AI_GEN_RESULT_SPLIT_LENGTH) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 生成错误");
        }
        
        String genChart = splits[1].trim();
        String genResult = splits[2].trim();
        // 插入到数据库
        Chart chart = new Chart();
        chart.setName(name);
        chart.setGoal(goal);
        chart.setChartData(csvData);
        chart.setChartType(chartType);
        chart.setGenChart(genChart);
        chart.setGenResult(genResult);
        chart.setUserId(loginUser.getId());
        boolean saveResult = chartService.save(chart);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "图表保存失败");
        BiResponse biResponse = new BiResponse();
        biResponse.setGenChart(genChart);
        biResponse.setGenResult(genResult);
        biResponse.setChartId(chart.getId());
        return ResultUtils.success(biResponse);
    }
    
    

    /**
     * 获取查询条件
     */
    @NotNull
    private QueryWrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest) {
        QueryWrapper<Chart> queryWrapper = new QueryWrapper<>();
        if (chartQueryRequest == null) {
            return queryWrapper;
        }
        Long id = chartQueryRequest.getId();
        String name = chartQueryRequest.getName();
        String goal = chartQueryRequest.getGoal();
        String chartType = chartQueryRequest.getChartType();
        Long userId = chartQueryRequest.getUserId();
        String sortField = chartQueryRequest.getSortField();
        String sortOrder = chartQueryRequest.getSortOrder();

        queryWrapper.eq(id != null && id > 0, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.eq(StringUtils.isNotBlank(goal), "goal", goal);
        queryWrapper.eq(StringUtils.isNotBlank(chartType), "chartType", chartType);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}
