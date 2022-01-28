package com.cyh.sidebar.bean;

import java.io.Serializable;
import java.util.Comparator;

public class AreaPhoneBean implements Serializable {
    public String name;         //地区名称
    public String name_py;      //地区名称拼音
    public String fisrtSpell;   //地区名称首字母
    public String code;         //地区代码
    public String locale;       //地区时区
    public String en_name;      //英文名


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_py() {
        return name_py;
    }

    public void setName_py(String name_py) {
        this.name_py = name_py;
    }

    public String getFisrtSpell() {
        return fisrtSpell;
    }

    public void setFisrtSpell(String fisrtSpell) {
        this.fisrtSpell = fisrtSpell;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    /**
     * 按拼音进行排序
     */
    public static class ComparatorPY implements Comparator<AreaPhoneBean> {
        @Override
        public int compare(AreaPhoneBean lhs, AreaPhoneBean rhs) {
            String str1 = lhs.name_py;
            String str2 = rhs.name_py;
            return str1.compareToIgnoreCase(str2);
        }
    }
}
