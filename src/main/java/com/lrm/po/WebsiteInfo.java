package com.lrm.po;

import lombok.Data;

import javax.persistence.*;

/*网站信息*/
@Entity
@Table(name = "t_website_info")
@Data
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


    @Override
    public String toString() {
        return "WebsiteInfo{" +
                "valueName='" + valueName + '\'' +
                ", value=" + value +
                '}';
    }

}
