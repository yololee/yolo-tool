package com.yolo.mybatisplus.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 */

@Data
@NoArgsConstructor
@ApiModel("分页数据返回结果")
public class TableDataInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "当前页")
    private long current;

    @ApiModelProperty(value = "每页的数量")
    private long size;

    /**
     * 列表数据
     */
    @ApiModelProperty(value = "列表数据")
    private transient List<T> rows;



    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }

    public static <T> TableDataInfo<T> build(IPage<T> page) {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setRows(page.getRecords());
        rspData.setTotal(page.getTotal());
        rspData.setCurrent(page.getCurrent());
        rspData.setSize(page.getSize());
        return rspData;
    }

    public static <T> TableDataInfo<T> build(List<T> list) {
        TableDataInfo<T> rspData = new TableDataInfo<>();
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
    }

}
