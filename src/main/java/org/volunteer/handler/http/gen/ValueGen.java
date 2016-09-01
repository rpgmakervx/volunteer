package org.volunteer.handler.http.gen;/**
 * Description : 
 * Created by YangZH on 16-8-29
 *  下午11:22
 */

import java.util.Random;

/**
 * Description :
 * Created by YangZH on 16-8-29
 * 下午11:22
 */

public class ValueGen {

    private static final String BASE_CHAR = "a0Ab1Bc2Cd3De4Ef5Fg6Gh7Hi8Ij9Jk!Kl@Lm#Mn$No%Op^Pq&Qr*Rs(St)Tu_Uv+Vw\\Wx|Xy.Yz<Z >";
    private String getRandomString(int length) { //length表示生成字符串的长度
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(BASE_CHAR.length());
            sb.append(BASE_CHAR.charAt(number));
        }
        return sb.toString();
    }

    private Integer getRandomInteger(int range){
        Random random = new Random();
        return random.nextInt(range);
    }

    private Float getRandomFloat(){
        Random random = new Random();
        return random.nextFloat();
    }

    private Boolean getRandomBool(){
        return System.currentTimeMillis()%2 ==0;
    }

    public Object getValue(Class clazz){
        if (clazz.equals(String.class)){
            return getRandomString(10);
        }else if (clazz.equals(Integer.class)){
            return getRandomInteger(Integer.MAX_VALUE);
        }else if (clazz.equals(Float.class)){
            return getRandomFloat();
        }else if (clazz.equals(Boolean.class)){
            return getRandomBool();
        }else
            return null;
    }
}
