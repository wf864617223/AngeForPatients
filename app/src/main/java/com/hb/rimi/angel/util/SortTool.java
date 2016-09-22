package com.hb.rimi.angel.util;

/**
 * 时间倒序排序
 * Created by Administrator on 2016/6/27.
 */

import com.hb.rimi.angel.bean.BAppointmentTime;
import com.hb.rimi.angel.bean.VisitList;

import java.util.Comparator;

public class SortTool implements Comparator<Object> {

    public int compare(Object o1, Object o2) {
        VisitList visitList1 = (VisitList) o1;
        VisitList visitList2 = (VisitList) o2;
        int flag = visitList2.getVISIT_DATE().compareTo(visitList1.getVISIT_DATE());
        return flag;
    }

}
