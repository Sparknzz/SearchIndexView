package com.nypzxy.searchforindex;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * Created by dance on 2017/4/3.
 */

public class ConvertPinYinUtils {
    /**
     * 获取指定汉字的拼音
     * @param chinese
     * @return
     */
    public static String getPinYin(String chinese){
        if(TextUtils.isEmpty(chinese))return null;

        //控制输出的拼音的格式，比如字母的大小写，需不需声调
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不要声调

        //黑马 -> heima
        //由于只支持单个汉字的获取，因此要将字符串转为字符数组，对每个汉字获取，最后拼接
        char[] charArr = chinese.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < charArr.length; i++) {
            char c = charArr[i];
            //1.过滤空格,选择忽略空格: 黑   马 -> heima
            if(Character.isWhitespace(c)){
                continue;
            }

            //2.过滤非中文字符:  &&a齐*天*大*圣a&&
            //简单判断：由于中文在utf8中占据3个字节(-128~127)，所以中文肯定大于127
            if(c > 127){
                //说明有可能是中文，获取它的拼音
                try {
                    //由于多音字的存在，比如： 单：[dan, chan, shan]
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    if(pinyinArr!=null){
                        //此处，我们取第0个，因为我们无能为力了。我们目前木办法去精准判断该字的读音。
                        //如果要实现精准读音，需要这样几个技术配合：分词算法，字库培养，服务器api支持.
                        //目前只有几个公司有这技术:腾讯， 阿里，搜狗，百度
                        builder.append(pinyinArr[0]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //说明获取失败，不用管
                }
            }else {
                //肯定不是中文，一般是那些因为字母：abcd，直接拼接即可
                builder.append(c);
            }

        }
        return builder.toString();
    }
}
