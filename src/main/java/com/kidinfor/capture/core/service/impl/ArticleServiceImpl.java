package com.kidinfor.capture.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kidinfor.capture.core.entity.Article;
import com.kidinfor.capture.core.service.ArticleService;
import com.kidinfor.capture.utils.ArticleSpider;
import com.kidinfor.capture.utils.analyzer.impl.CsdnWeeklyDocumentAnalyzer;

/**
 * 文章service实现
 * Created by anxpp.com on 2017/3/11.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Value("${csdn.weekly.preurl}")
    private String preUrl;
    @Resource
    private CsdnWeeklyDocumentAnalyzer csdnWeeklyDocumentAnalyzer;
//    @Autowired
//    private ArticleRepo articleRepo;

    /**
     * 根据期号获取文章列表
     *
     * @param stage 期号
     * @return 文章列表
     */
    @Override
    @Cacheable(value = "reportcache", keyGenerator = "csdnKeyGenerator")
    public List<Article> forWeekly(Integer stage) throws Exception {
        List<Article> articleList = ArticleSpider.forEntityList(preUrl + stage, csdnWeeklyDocumentAnalyzer, Article.class);
        articleList.forEach(article -> article.setStage(stage));
//        return articleRepo.save(articleList);
        return articleList;
    }
}