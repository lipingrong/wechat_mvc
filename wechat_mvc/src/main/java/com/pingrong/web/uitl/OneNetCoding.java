package com.pingrong.web.uitl;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * Created by zhangchen on 2016/8/2.
 * 依赖MAVEN：
 * <dependency>
 * <groupId>org.json</groupId>
 * <artifactId>json</artifactId>
 * <version>20160212</version>
 * </dependency>
 * <dependency>
 * <groupId>org.bouncycastle</groupId>
 * <artifactId>bcprov-jdk15on</artifactId>
 * <version>1.54</version>
 * </dependency>
 * <dependency>
 * <groupId>commons-codec</groupId>
 * <artifactId>commons-codec</artifactId>
 * <version>1.10</version>
 * </dependency>
 *
 * 5xmTBxxZb7sUKsOEX6t3mFRmzpTwn6SHs4Bt2cLmPRS
 * token  ceshionenet
 */
public class OneNetCoding {
    private static MessageDigest mdInst;

    static {
        try {
            mdInst = MessageDigest.getInstance("MD5");
            Security.addProvider(new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查消息摘要
     * token为用户填写的平台token
     * 方法非线程安全
     */
    public static boolean checkSignature(BodyObj obj, String token) {
        //计算接受到的消息的摘要
        //token长度 + 8B随机字符串长度 + 消息长度
        byte[] signature = new byte[token.length() + 8 + obj.getMsg().length()];
        System.arraycopy(token.getBytes(), 0, signature, 0, token.length());
        System.arraycopy(obj.getNonce().getBytes(), 0, signature, token.length(), 8);
        System.arraycopy(obj.getMsg().getBytes(), 0, signature, token.length() + 8, obj.getMsg().length());
        mdInst.update(signature);
        String calSig = Base64.encodeBase64String(mdInst.digest());
        return calSig.equals(obj.getMsgSignature());
    }

    /**
     * 解密消息
     * encodeKey为平台为用户生成的AES的BASE64编码格式秘钥
     */
    public static String decryptMsg(BodyObj obj, String encodeKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] encMsg = Base64.decodeBase64(obj.getMsg());
        byte[] aeskey = Base64.decodeBase64(encodeKey + "=");
        SecretKey secretKey = new SecretKeySpec(aeskey, 0, 32, "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(aeskey, 0, 16));
        byte[] allmsg = cipher.doFinal(encMsg);
        byte[] msgLenBytes = new byte[4];
        System.arraycopy(allmsg, 16, msgLenBytes, 0, 4);
        int msgLen = getMsgLen(msgLenBytes);
        byte[] msg = new byte[msgLen];
        System.arraycopy(allmsg, 20, msg, 0, msgLen);
        return new String(msg);
    }

    /**
     * body为http请求的body部分
     * encrypted表征是否为加密消息
     */
    public static BodyObj resolveBody(String body, boolean encrypted) {
        JSONObject jsonMsg = new JSONObject(body);
        BodyObj obj = new BodyObj();
        obj.setNonce(jsonMsg.getString("nonce"));
        obj.setMsgSignature(jsonMsg.getString("msg_signature"));
        if (encrypted) {
            if (!jsonMsg.has("enc_msg")) {
                return null;
            }
            obj.setMsg(jsonMsg.getString("enc_msg"));
        } else {
            if (!jsonMsg.has("msg")) {
                return null;
            }
            obj.setMsg(jsonMsg.getString("msg"));
        }
        return obj;
    }

    /**
     * 转换未加密MSG字符串为对象
     * @return
     */
    public static MsgObj resolveMsg(String msg){
        JSONObject jsonMsg = new JSONObject(msg);
        MsgObj msgObj = new MsgObj();
        if (!jsonMsg.has("type")) {
            System.out.println("kong");
            return null;
        }
        msgObj.setAt(jsonMsg.getInt("at"));
        msgObj.setType(jsonMsg.getInt("type"));
        msgObj.setDs_id(jsonMsg.getString("ds_id"));
        msgObj.setValue(jsonMsg.getInt("value"));
        msgObj.setDev_id(jsonMsg.getInt("dev_id"));
        return  msgObj;
    }

    private static int getMsgLen(byte[] arrays) {
        int len = 0;
        len += (arrays[0] & 0xFF) << 24;
        len += (arrays[1] & 0xFF) << 16;
        len += (arrays[2] & 0xFF) << 8;
        len += (arrays[3] & 0xFF);
        return len;
    }

    public static class BodyObj {
        private String msg;
        private String nonce;
        private String msgSignature;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getMsgSignature() {
            return msgSignature;
        }

        public void setMsgSignature(String msgSignature) {
            this.msgSignature = msgSignature;
        }
    }
    public static class MsgObj{
        private Integer at;
        private Integer type;
        private String ds_id;
        private Integer value;
        private Integer dev_id;

        public Integer getAt() {
            return at;
        }

        public void setAt(Integer at) {
            this.at = at;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getDs_id() {
            return ds_id;
        }

        public void setDs_id(String ds_id) {
            this.ds_id = ds_id;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public Integer getDev_id() {
            return dev_id;
        }

        public void setDev_id(Integer dev_id) {
            this.dev_id = dev_id;
        }
    }
}
