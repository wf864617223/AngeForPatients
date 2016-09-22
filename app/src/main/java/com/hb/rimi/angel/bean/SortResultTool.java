package com.hb.rimi.angel.bean;

/**
 * paixu 
 * Created by Administrator on 2016/6/27.
 */



import java.util.Comparator;

public class SortResultTool implements Comparator<Object> {

    public int compare(Object o1, Object o2) {
        UserYuyue.ResultBean visitList1 = (UserYuyue.ResultBean) o1;
        UserYuyue.ResultBean visitList2 = (UserYuyue.ResultBean) o2;
        int flag = visitList1.getBESPOKE_TIME().compareTo(visitList2.getBESPOKE_TIME());
        return flag;
    }
}
