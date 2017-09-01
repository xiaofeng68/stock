package com.kidinfor.capture.core.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kidinfor.capture.core.entity.Article;

/**
 * 文章数据访问层
 * Created by anxpp.com on 2017/3/11.
 */
public interface ArticleRepo extends JpaRepository<Article, Long> {
}
