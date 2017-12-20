package com.kidinfor.capture.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kidinfor.capture.core.entity.StockCode;
import com.kidinfor.capture.core.service.StockService;

/**
 * 默认页面
 * Created by anxpp.com on 2017/3/11.
 */
@Controller
@RequestMapping("/stock")
public class StockController {
    @Resource
    private StockService stockService;

    /**
     * 更新股票代码
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/code/update")
    public void updateCodes() throws Exception {
        stockService.updateCodes();
    }
    
    /**
     * 更新十大股东
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/holder/update/{code}")
    public String updateHolders(@PathVariable("code") String code) throws Exception {
    	stockService.truncateHolders();
    	List<StockCode> list = stockService.getCodes();
    	for(StockCode c : list){
    		try{
    			stockService.updateHolders(c.getCode());
    		}catch(Exception e){
    			continue;
    		}
    	}
    	return "{sucess:true}";
    }
    /**
     * 获取K线
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/price/update/{code}")
    public String updatePrice(@PathVariable("code") String code) throws Exception {
    	stockService.truncatePrice();
    	List<StockCode> list = stockService.getCodes();
    	for(StockCode c : list){
    		try{
    			stockService.updatePrice(c.getCode());
    		}catch(Exception e){
    			continue;
    		}
    	}
    	return "{sucess:true}";
    }
    /**
     * 获取基金持仓（基金持仓变动配合控盘操作-长线）
     * @param code
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/jijinCC/update")
    public String updateJijinCC() throws Exception {
		stockService.updateJijinCC();
    	return "{sucess:true}";
    }
    @ResponseBody
    @GetMapping("/jijinBD/update")
    public String updateJijinBD() throws Exception {
    	stockService.truncateJijinBD();
    	List<String> list = stockService.findJJCode();
    	for(String code : list) {
    		stockService.updateJijinBD(code);
    	}
    	return "{sucess:true}";
    }
    @ResponseBody
    @GetMapping("/type/update/{code}")
    public String updateType(@PathVariable("code") String code) throws Exception {
//    	stockService.truncatePrice();
//    	List<StockCode> list = stockService.getCodes();
//    	for(StockCode c : list){
//    		try{
    			stockService.updateType("sz002068","002068");
//    		}catch(Exception e){
//    			continue;
//    		}
//    	}
    	return "{sucess:true}";
    }
    
}