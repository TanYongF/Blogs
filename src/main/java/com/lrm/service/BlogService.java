package com.lrm.service;

import com.lrm.po.Blog;
import com.lrm.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by limi on 2017/10/20.
 */

public interface BlogService {


    /**
     * 根据id获取博客
     * @param id    Blog ID
     * @return      Blog
     **/
    Blog getBlog(Long id);

    /**
     * 转换Markdown格式笔记为 Html 格式便于显示
     * @param id    blogID
     * @return      Blog对象
     **/
    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable,BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    /**
     * 通过tagId进行查找博客内容
     * @param tagId
     * @param pageable
     * @return
     **/
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    /**
     * 通过query来查询博客
     * @param query             关键字
     * @param pageable          页面
     * @return                  Page
     */
    Page<Blog> listBlog(String query,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Map<String,List<Blog>> archiveBlog();

    /**
     * 计算Blog的总个数
     * @return         Blog个数
     */
    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
