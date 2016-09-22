package com.hb.rimi.angel.util;

/**
 * 时间倒序排序
 * Created by Administrator on 2016/6/27.
 */

import com.hb.rimi.angel.bean.BAppointmentTime;
import com.hb.rimi.angel.bean.VisitList;

import java.util.Comparator;

public class SortToolNew implements Comparator<Object> {

    public int compare(Object o1, Object o2) {
        BAppointmentTime bAppointmentTime1 = (BAppointmentTime) o1;
        BAppointmentTime bAppointmentTime2 = (BAppointmentTime) o2;
        int flag = bAppointmentTime2.getDate().compareTo(bAppointmentTime1.getDate());
        return flag;
    }

}
