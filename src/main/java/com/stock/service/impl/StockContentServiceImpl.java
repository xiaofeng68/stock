package com.stock.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.stock.dao.StockContentMapper;
import com.stock.service.StockContentService;
@Service
public class StockContentServiceImpl implements StockContentService {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    StockContentMapper stockContentMapper;
    @Override
    public List<Map<String, Object>> synNewStocks(File file) {
        try {
            InputStream is = new FileInputStream(file);
            Workbook rwb = Workbook.getWorkbook(is);
            Sheet sheet = rwb.getSheet("listTable");
            int cr = sheet.getRows();
            String header = "";
            String preheader = "";
            List<String> fieldsList = new ArrayList<String>();
            List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
            for (int i = 1; i < cr; i++) {
                Cell[] testcell = sheet.getRow(i);
                if (testcell.length == 0)
                    continue;
                if (!header.equals(preheader)) {
                    fieldsList.clear();
                    preheader = header;
                }
                String tempString = testcell[0].getContents();
                int datatype = tempString.indexOf("&&");
                if (datatype != -1)
                    continue;
                Map<String,Object> model = new HashMap<String,Object>();
                model.put("code",testcell[0].getContents().trim());
                model.put("name",testcell[1].getContents().trim());
                model.put("bus",testcell[2].getContents().trim());
                String cache = testcell[3].getContents().trim();
                if (cache.indexOf("亿") != -1) {
                    cache = cache.substring(0, cache.length() - 1);
                    model.put("capital",Double.parseDouble(cache) * 1E8);
                } else if (cache.indexOf("万") != -1) {
                    cache = cache.substring(0, cache.length() - 1);
                    model.put("capital",Double.parseDouble(cache) * 1E4);
                } else {
                    model.put("capital",Double.parseDouble(cache));
                }
                dataList.add(model);
            }
            log.debug(file.getPath() + "解析完毕");
            return dataList;
        } catch (Exception ex) {
            log.error(file.getPath() + "解析失败,错误原因：" + ex);
        }
        return null;
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public int synStockContent(Map<String, Object> stockContent) {
        return stockContentMapper.synStockContent(stockContent);
    }

}
