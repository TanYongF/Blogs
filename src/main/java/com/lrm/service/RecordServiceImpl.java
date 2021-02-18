package com.lrm.service;

import com.lrm.dao.RecordRepository;
import com.lrm.po.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Record recording(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public List<Record> getAll() {
        return recordRepository.findAll();
    }
}
