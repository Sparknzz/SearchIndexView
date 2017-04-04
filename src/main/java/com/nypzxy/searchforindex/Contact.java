package com.nypzxy.searchforindex;

import android.support.annotation.NonNull;

/**
 * Created by Rita on 4/4/2017.
 */

public class Contact implements Comparable<Contact>{
    public String name;
    public String alphabet;

    public Contact(String name) {
        this.name = name;
        this.alphabet = ConvertPinYinUtils.getPinYin(name);

    }

    @Override
    public int compareTo(@NonNull Contact c) {
        //返回0说明相同  返回值不为0则不相同 -1则排在前面  1为大 排在后面
        return this.alphabet.compareTo(c.alphabet);
    }
}
