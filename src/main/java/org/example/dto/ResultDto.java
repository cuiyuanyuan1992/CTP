package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "构建结果对象", description = "构建结果对象")
public class ResultDto {
    /**
     * 失败用例
     */
    @ApiModelProperty(value = "失败用例")
    private int failCount;

    /**
     * 成功用例
     */
    @ApiModelProperty(value = "成功用例")
    private int passCount;

    /**
     * 跳过用例
     */
    @ApiModelProperty(value = "跳过用例")
    private int skipCount;

    /**
     * 成功率
     */
    @ApiModelProperty(value = "成功率")
    private float successRate;
}
