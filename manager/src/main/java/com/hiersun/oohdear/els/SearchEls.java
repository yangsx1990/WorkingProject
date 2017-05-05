package com.hiersun.oohdear.els;/**
 * Created by liubaocheng on 2017/3/7.
 */

import com.hiersun.oohdear.entity.ArticleInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Description:   搜索elasticSearch
 * Author: liubaocheng
 * Create: 2017-03-07 9:48
 **/
public interface SearchEls extends ElasticsearchRepository<ArticleInfo,String> {

}
