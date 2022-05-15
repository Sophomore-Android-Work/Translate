package service;

import mapper.MydictMapper;
import pojo.Mydict;

public class MydictServiceImpl implements MydictService{
    private MydictMapper mydictMapper;

    public void setMydictMapper(MydictMapper mydictMapper) {
        this.mydictMapper = mydictMapper;
    }

    public Mydict selectByEng(String eng){
        return mydictMapper.selectByEng(eng);
    };
}
