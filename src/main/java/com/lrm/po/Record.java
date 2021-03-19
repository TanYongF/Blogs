package com.lrm.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 *  网站访问记录
 */
@Table(name = "t_record")
@Entity
@NoArgsConstructor
@Data
@ToString
public class Record {

    @Id
    @GeneratedValue
    private Long id;

    private String ip;

    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisitTime;

    /**
     * 访问总次数
     */
    private Long totalNumberOfVisits;
    
}
