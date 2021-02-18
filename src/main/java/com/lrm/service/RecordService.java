package com.lrm.service;

import com.lrm.po.Record;

import java.util.List;

public interface RecordService {

    /**
     * 保存一条记录
     * @param record
     * @return
     */
    public Record recording(Record record);

    /**
     * 获取所有记录
     * @return
     */
    public List<Record> getAll();

}
