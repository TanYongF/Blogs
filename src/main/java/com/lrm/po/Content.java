package com.lrm.po;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Basic;
import javax.persistence.FetchType;

/**
 * @Describe: 文档
 * @Author: tyf
 * @CreateTime: 2021/3/15
 **/

@Data
@ToString
@Accessors(chain = true)
@Document(collection = "content")
public class Content {

    @Id
    private String id;

    @Field("content")
    @Basic(fetch = FetchType.LAZY)
    private String content;




}
