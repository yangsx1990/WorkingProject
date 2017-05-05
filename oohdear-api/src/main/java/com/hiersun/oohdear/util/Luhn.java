package com.hiersun.oohdear.util;

/**
 * date 2016/11/15 15:24
 *
 * @author Leon yang_xu@hiersun.com
 * @version V1.0
 */
public class Luhn {

    public static String generateLuhnNo(String originNo){

        // 填充“0”字符占位
        int originLength = originNo.length();
        //String tempStr = originNo.substring(0, originLength - 1) + "0" + originNo.substring(originLength - 1) + "0";
        String t1 = originNo.substring(0, originLength - 1);
        String t2 = originNo.substring(originLength - 1);
        String tempStr = t1 + "0" + t2 + "0";
        // 根据Luhn算法计算奇偶数位和
        int sumOdd = 0;
        int sumEven = 0;
        int tempLength = tempStr.length();
        int[] numArr = new int[tempLength];
        for (int i = 0; i < tempLength; i++) {
            numArr[i] = Integer.parseInt(tempStr.substring(tempLength - i - 1, tempLength - i));
        }
        for (int i = 0; i < tempLength; i++) {
            if(i % 2 == 0){
                sumOdd += numArr[i];
            }else{
                int temp = numArr[i] * 2;
                if(temp > 9){
                    sumEven += (temp / 10 + temp % 10);
                }else{
                    sumEven += temp;
                }
            }
        }

        // 根据整除取除数因子作为倒数第3位数
        int sumAll = sumOdd + sumEven;
        int num_3 = 1;
        for(int i=9;i>1;i--){
            if(sumAll % i == 0){
                num_3 = i;
                break;
            }
        }

        // 获取倒数第1位数（根据源数据加倒数第3位数后结合Luhn算法取差补）
        int remainder = (sumOdd + sumEven + num_3) % 10;
        int num_1 = remainder == 0 ? 0 : 10 - remainder;

        return originNo.substring(0, originLength - 1) + num_3 + originNo.substring(originLength - 1) + num_1;
    }
}
