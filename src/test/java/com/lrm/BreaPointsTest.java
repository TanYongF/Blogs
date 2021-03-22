package com.lrm;

import org.junit.Test;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/3/20
 **/


//
//int main(){
//    //逻辑
//        }
public class BreaPointsTest {



    public  static int breakPoint(){
        int i = 1 ;
        return  i  / 0;
    }


    public static void main(String[] args) {
        breakPoint();
    }


}
