package com.lrm.po;

import javax.persistence.*;
import java.util.Date;

/**
 *  网站访问记录
 */
@Table(name = "t_record")
@Entity
public class Record {

    @Id
    @GeneratedValue
    private Long id;

    private String ip;

    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    /*所在城市经纬度*/
    private String rectangle;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", address='" + address + '\'' +
                ", dateTime=" + dateTime +
                ", rectangle='" + rectangle + '\'' +
                '}';
    }

    public String getRectangle() {
        return rectangle;
    }

    public void setRectangle(String rectangle) {
        this.rectangle = rectangle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Record() {
    }
}
