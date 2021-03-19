package com.lrm.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author tyf
 * @Description
 * @Date 12:58 2021/3/15
 **/

@Entity
@Table(name = "t_website_info")
@Data
@ToString
public class WebsiteInfo {

    /**
     * 网站各值名称
     */
    @Id
    private String valueName;


    /**
     * 网站各值
     */
    private String value;

    public WebsiteInfo() {
    }




}
