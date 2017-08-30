package com.stock.service;

import java.io.File;
import java.util.List;
import java.util.Map;


 /**
 * @ClassName:     StockContentService.java
 * @Description:   股票基本信息
 * @author         (作者)
 * @version        V1.0  
 * @Date           2014-12-6 上午9:52:17 
 */
 
public interface StockContentService {
    /**   
     * @Title: synNewStocks   
     * @Description: 根据文件解析股票信息 
     * @param file   文件格式：编号、名称、行业、流通股本
     * @return
     * @author  author
     */
     
    List<Map<String,Object>> synNewStocks(File file);
    /**   
     * @Title: synStockContent   
     * @Description: 添加或更新个股的基本信息 
     * @param stockContent
     * @return
     * @author  author
     */
    int synStockContent(Map<String,Object> stockContent);
}
