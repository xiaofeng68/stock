package com.kidinfor.capture.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @GetMapping("/code/update")
    public void updateCodes() throws Exception {
        stockService.updateCodes();
    }
    
    @ResponseBody
    @GetMapping("/holder/update/{code}")
    public void updateHolders(@PathVariable("code") String code) throws Exception {
        stockService.updateHolders(code);
    }
}