package com.pingrong.web.controller;

import com.pingrong.web.uitl.OneNetAPI;
import com.pingrong.web.uitl.OneNetCoding;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/27.
 */
@Controller
public class OneNetController {
    private String AESKey = "5xmTBxxZb7sUKsOEX6t3mFRmzpTwn6SHs4Bt2cLmPRS";
    /**
     * onenet验证接口是否可用
     * @param msg
     * @param nonce
     * @param signature
     * @return
     */
    @RequestMapping(value = "onenet",method = RequestMethod.GET)
    @ResponseBody
    public String onenet(String msg,String nonce,String signature){
        System.out.println(msg);
        System.out.println(nonce);
        System.out.println(signature);
        return msg;
    }

    /**
     * onenet主动传输数据接口，必须与验证接口路径相同，只是方式不同
     * @param request
     */
    @RequestMapping(value = "onenet",method = RequestMethod.POST)
    @ResponseBody
    public void onenetJie(HttpServletRequest request){
        BufferedReader br;
        try {
            //获取boby中的数据流
            br = request.getReader();
            String str;
            StringBuffer wholeStr = new StringBuffer("");
            while((str = br.readLine()) != null){
                wholeStr.append(str);
            }
            //将流转换成对象
            OneNetCoding.BodyObj bodyObj = OneNetCoding.resolveBody(wholeStr.toString(), true);
            //解码对象msg属性值
            String msg = OneNetCoding.decryptMsg(bodyObj, AESKey);
            System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date()) + msg);
            /*OneNetCoding.MsgObj msgObj = OneNetCoding.resolveMsg(msg);
            System.out.println(msgObj.getDs_id());
            System.out.println(msgObj.getValue());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
