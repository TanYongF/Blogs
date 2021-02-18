package com.lrm.service;

import com.alibaba.fastjson.JSONObject;
import com.lrm.dao.RecordRepository;
import com.lrm.po.Record;
import com.lrm.util.HttpClient;
import com.lrm.util.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public void recording(HttpServletRequest httpServletRequest) {

        /*网站访问记录*/
        String ip = IPUtils.getIpAddr(httpServletRequest);

        Record record = recordRepository.findByIp(ip);
        if(null == record){
            record = new Record();
            record.setIp(ip);
            record.setLastVisitTime(new Date());
            String getAddressByIpRequestUrl = "https://restapi.amap.com/v3/ip?key=0113a13c88697dcea6a445584d535837";
            record.setTotalNumberOfVisits(new Long(1));
            getAddressByIpRequestUrl += "&IP="+ip;
            String result = HttpClient.doGet(getAddressByIpRequestUrl);
            JSONObject ipObject = JSONObject.parseObject(result);
            System.out.println(ipObject);
            record.setAddress(ipObject.getString("province")+ipObject.getString("city"));
            record.setRectangle(ipObject.getString("rectangle"));
        }else {
            record.setLastVisitTime(new Date());
            record.setTotalNumberOfVisits(record.getTotalNumberOfVisits()+1);
        }

        recordRepository.save(record);
    }

    @Override
    public List<Record> getAll() {
        return recordRepository.findAll();
    }

    @Override
    public Record findByIp(String ip) {
        return recordRepository.findByIp(ip);
    }
}
