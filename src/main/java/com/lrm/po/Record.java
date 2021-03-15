package com.lrm.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 *  网站访问记录
 */
@Table(name = "t_record")
@Entity
@NoArgsConstructor
@Data
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

    @Override
    public String toString() {
        return "访问记录[" +
                "记录ID:" + id +
                ", IP='" + ip + '\'' +
                ", 地区='" + address + '\'' +
                ", 访问时间=" + lastVisitTime +
                ", 总访问次数=" + totalNumberOfVisits +
                ']';
    }
}
