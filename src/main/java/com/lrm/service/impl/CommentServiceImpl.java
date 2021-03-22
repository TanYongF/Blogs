package com.lrm.service.impl;

import com.lrm.dao.CommentRepository;
import com.lrm.po.Comment;
import com.lrm.service.CommentService;
import com.lrm.util.PushWechatMessageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by limi on 2017/10/22.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void deleteCommentByBlogId(Long blogId) {
        commentRepository.deleteByBlogId(blogId);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {

        String title = "博客评论通知";

        String content =
                "评论博客标题:"+comment.getBlog().getTitle()+
                "<br>用户名称:"+comment.getNickname()+
                "<br>用户邮箱:"+comment.getEmail()+
                "<br>评论内容:"+comment.getContent()+
                "<br>评论时间:"+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());

        String result = PushWechatMessageUtil.pushMessageByPost(title,content);
        System.out.println("result = " + result);

        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }


    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }


    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<Comment> tempReplays = new ArrayList<>();


    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replays = comment.getReplyComments();
            for(Comment replay : replays) {
                //循环迭代，找出子代，存放在tempReplays中
                recursively(replay);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplays);
            //清除临时存放区
            tempReplays = new ArrayList<>();
        }
    }



    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        //顶节点添加到临时存放集合
        tempReplays.add(comment);
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
