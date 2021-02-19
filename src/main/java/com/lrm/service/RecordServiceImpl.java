package com.lrm.service;

import com.alibaba.fastjson.JSONObject;
import com.lrm.dao.RecordRepository;
import com.lrm.po.Record;
import com.lrm.util.HttpClient;
import com.lrm.util.IPUtils;
import com.lrm.util.PushWechatMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
            record.setTotalNumberOfVisits(new Long(1));
            String getAddressByIpRequestUrl = "https://www.maitube.com/ip/?ip="+record.getIp();

            String result = HttpClient.doGet(getAddressByIpRequestUrl);

            record.setAddress(result.substring(15,result.length()));

            Record saveRecord =  recordRepository.save(record);

            /*推送微信消息*/
            String title = "新的IP地址访问通知";
            String content =
                    "访问记录ID:" + saveRecord.getId()+
                    "<br>访问IP地址:"+record.getIp()+
                    "<br>访问地区:"+record.getAddress()+
                    "<br>访问时间:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            PushWechatMessageUtil.pushMessageByPost(title,content);

            return;
        }else {
            record.setLastVisitTime(new Date());
            record.setTotalNumberOfVisits(record.getTotalNumberOfVisits()+1);
            recordRepository.save(record);
        }

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
