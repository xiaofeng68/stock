package com.authority.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.service.StockContentService;

/**
 * @author 
 * 导入新股 1：打开东方财富通软件-》找到新股或深沪A股-》右键导出所有股票-》格式选择：编号、名称、行业
 *            导出完毕后，运行该测试类，即可同步完所有的股票 
 *         2：更新股票的基本信息 3：更新股票的年统计月统计
 * @date 2011-12-7 下午2:27:04
 */
public class BaseModulesServiceTest extends ServicesTest {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private StockContentService stockContentService;

    @Test
    public void selectAllModules() {
        
        // 同步个股信息：编号、名称、行业
        List<Map<String, Object>> list = stockContentService.synNewStocks(new File("d:/123.xls"));
        if (list != null && list.size() > 0) {
            int count=0;
            for(Map<String,Object> map : list){
                try{
                if(stockContentService.synStockContent(map)>0){
                    count++;
                }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.info("个股总数：{}个,更新了{}个",list.size(),count);
        }
        //删除各个表中不再这里面的数据
    }
}
