package com.lrm.service.impl;

import com.lrm.NotFoundException;
import com.lrm.dao.BlogRepository;
import com.lrm.dao.ContentRepository;
import com.lrm.po.Blog;
import com.lrm.po.Content;
import com.lrm.po.Type;
import com.lrm.service.BlogService;
import com.lrm.service.CommentService;
import com.lrm.util.MarkdownUtils;
import com.lrm.util.MyBeanUtils;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by limi on 2017/10/20.
 */
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentRepository contentRepository;


    @Override
    public Blog getBlog(Long id) {
        Blog one = blogRepository.findOne(id);
        one.setContent(contentRepository.findOne(one.getContentId()).getContent());
        return blogRepository.findOne(id);
    }


    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findOne(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        Content content = contentRepository.findOne(b.getContentId());
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content.getContent()));

        //访问量 + 1
        blogRepository.updateViews(id);
        return b;
    }


    /**
     *
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return  blogRepository.findAll(pageable);
    }


    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

//    /**
//     * FIXME:通过查找关键字来实现查找响应博客
//     * @param query
//     * @param pageable
//     * @return  Page
//     */
//    @Override
//    public Page<Blog> listBlog(String query, Pageable pageable) {
//        return blogRepository.findByQuery(query,pageable);
//    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        List<Blog> blogs = blogRepository.findTop(pageable);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<String, List<Blog>>();
        for (String year : years) {
            List<Blog> byYear = blogRepository.findByYear(year);
            Collections.reverse(byYear);
            map.put(year, byYear);
        }
        return map;
    }

    /**
     * 计算Blog的总个数
     * @return         Blog个数
     */
    @Override
    public Long countBlog() {
        return blogRepository.count();
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        Content content = new Content();
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        //保存到MongoDB
        content.setContent(blog.getContent());
        Content save = contentRepository.save(content);
        blog.setContentId(save.getId());
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Content content = new Content();
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        //保存到MongoDB
        content.setId(b.getContentId());
        content.setContent(b.getContent());
        contentRepository.save(content);
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findOne(id);
        //删除content
        contentRepository.delete(blog.getContentId());
        //删除comment
        commentService.deleteCommentByBlogId(id);
        //删除blog
        blogRepository.delete(id);

    }
}
