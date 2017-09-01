package com.kidinfor.capture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kidinfor.capture.core.entity.Article;
import com.kidinfor.capture.core.service.ArticleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 默认页面
 * Created by anxpp.com on 2017/3/11.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @ResponseBody
    @GetMapping("/get/stage/{stage}")
    public List<Article> getArticleByStage(@PathVariable("stage") Integer stage) throws Exception {
        return articleService.forWeekly(stage);
    }
}