package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.SortResultTool;
import com.hb.rimi.angel.bean.UserYuyue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/7/6.
 */
public class UserYuyueStyleTwoAdapter extends BaseExpandableListAdapter {
    Context context;
    // 父含子层
    ArrayList<UserYuyue.ResultBean> father_array = new ArrayList<UserYuyue.ResultBean>();
    //private List<UserYuyue.ResultBean> groupArray;//组列表
    //private List<UserYuyue.ResultBean> childArray;//子列表
    private List<UserYuyue.ResultBean> result1;
    //    private ArrayList<String> father_array = new ArrayList<>();//父层
//    private ArrayList<List<String>> son_array = new ArrayList<>();//子层
    private LayoutInflater father_Inflater = null;
    private LayoutInflater son_Inflater = null;

    public UserYuyueStyleTwoAdapter(Context context, List<UserYuyue.ResultBean> result1) {
        this.context = context;
        this.result1 = result1;
        father_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        son_Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (result1 != null && result1.size() > 0) {
            initData(result1);
        }
    }

    @Override
    public int getGroupCount() {
        return father_array.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return son_array.get(groupPosition).size();
        return father_array.get(groupPosition).getResultBeans().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return father_array.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return father_array.get(groupPosition).getResultBeans().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Father_ViewHolder father_viewHolder = null;
        if (convertView == null) {
            convertView = father_Inflater.inflate(R.layout.listview_group, null);
            father_viewHolder = new Father_ViewHolder(convertView);
            convertView.setTag(father_viewHolder);
        } else {
            father_viewHolder = (Father_ViewHolder) convertView.getTag();
        }
        /*for (int i = 0; i < groupArray.size(); i++) {
            if (i + 1 < groupArray.size()) {
                if ((groupArray.get(i).getDOCTOR_NAME()).equals(groupArray.get(i + 1).getDOCTOR_NAME()) &&
                        (groupArray.get(i).getBESPOKE_TIME()).equals(groupArray.get(i + 1).getBESPOKE_TIME())) {
                    //groupArray.add(i, groupArray.get(i).getDOCTOR_NAME()+ groupArray.get(i).getBESPOKE_TIME());
                    father_viewHolder.textGroup.setText(groupArray.get(i).getDOCTOR_NAME() + groupArray.get(i).getBESPOKE_TIME());
                }
            }
        }*/
//        String s = father_array.get(groupPosition);
//        String[] split = s.split(" ");
//        father_viewHolder.textGroup.setText(split[0]);
//        father_viewHolder.textGroup2.setText(split[1]);

        father_viewHolder.textGroup.setText(father_array.get(groupPosition).getDOCTOR_NAME());
        father_viewHolder.textGroup2.setText(father_array.get(groupPosition).getBESPOKE_TIME() != null && father_array.get(groupPosition).getBESPOKE_TIME().length() > 10 ? father_array.get(groupPosition).getBESPOKE_TIME().substring(0,10) : "");

        ImageView mgroupimage = (ImageView) convertView.findViewById(R.id.lg_iv);
        mgroupimage.setImageResource(R.mipmap.icon_aa_right_down);
        if (!isExpanded) {
            mgroupimage.setImageResource(R.mipmap.icon_aa_right);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Son_ViewHolder son_viewHolder = null;
        if (convertView == null) {
            convertView = son_Inflater.inflate(R.layout.listview_chlid, null);

            son_viewHolder = new Son_ViewHolder(convertView);
            convertView.setTag(son_viewHolder);
        } else {
            son_viewHolder = (Son_ViewHolder) convertView.getTag();
        }
        /*for(int i = 0; i< childArray.size(); i++){
            if(i+1< childArray.size()){
                while((childArray.get(i).getDOCTOR_NAME()).equals(childArray.get(i+1).getDOCTOR_NAME())&&
                        (childArray.get(i).getBESPOKE_TIME()).equals(childArray.get(i+1).getBESPOKE_TIME())){
                        //child2Array.add(j, childArray.get(j).getITEM_NAME());
                        son_viewHolder.textChild.setText(childArray.get(i).getITEM_NAME());
                        System.out.println("=子数据=>"+childArray.get(i).getITEM_NAME());
                    T.ShowToast(context,"=子数据=>"+childArray.get(i).getITEM_NAME());
                    //childArray.add(i,child2Array);
                    //addInfo(result1.get(i).getDOCTOR_NAME()+ result1.get(i).getBESPOKE_TIME(),child2Array);
                }
            }
        }*/
        son_viewHolder.textChild.setText(father_array.get(groupPosition).getResultBeans().get(childPosition).getITEM_NAME());
//        son_viewHolder.textChild.setText(son_array.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //初始化数据，主要是父层和子层数据的初始化
//    public void initData() {
//        List<String> child2Array = new ArrayList<>();
//        int j = 0;
//        int k = 0;
//        for (int i = 0; i < result1.size(); i++) {
//            if ((i + 1) < result1.size()) {
//                if ((result1.get(i).getDOCTOR_NAME()).equals(result1.get(i + 1).getDOCTOR_NAME()) && (result1.get(i).getBESPOKE_TIME()).equals(result1.get(i + 1).getBESPOKE_TIME())) {
//                    if (i == 0) {
//                        String bespoke_time = result1.get(i).getBESPOKE_TIME();
//                        String[] split = bespoke_time.split(" ");
//                        father_array.add(j, result1.get(i).getDOCTOR_NAME() + " " + split[0]);
//                        j++;
//                    } else {
//
//                        child2Array.add(k, result1.get(i).getITEM_NAME());
//                        son_array.add(k, child2Array);
//                        k++;
//                    }
//                } else {
//                    father_array.add(j, result1.get(i).getDOCTOR_NAME() + result1.get(i).getBESPOKE_TIME());
//                    j++;
//                }
//
//            }
//        }
//    }
    // 初始化数据，主要是父层和子层数据的初始化
    public void initData(List<UserYuyue.ResultBean> beans) {

        for (int i = 0; i < beans.size(); i++) {

            if (father_array.size() > 0) {
                // 添加父组之前要先查找父组是否存在 主键为 name 和 时间
                for (int j = 0; j < father_array.size(); j++) {
                    String DOCTOR_NAME = father_array.get(j).getDOCTOR_NAME();
                    String BESPOKE_TIME = father_array.get(j).getBESPOKE_TIME();
                    // 如果找到数组中有医生和时间，那么不添加父
                    if ((DOCTOR_NAME).equals(beans.get(i).getDOCTOR_NAME())
                            && (BESPOKE_TIME).equals(beans.get(i)
                            .getBESPOKE_TIME())) {
                        break;

                    } else {
                        // 如果没找到数组中有医生和时间，那么添加父
                        UserYuyue.ResultBean resultBean = new UserYuyue.ResultBean();
                        resultBean.setBESPOKE_TIME(beans.get(i)
                                .getBESPOKE_TIME());
                        resultBean
                                .setDOCTOR_NAME(beans.get(i).getDOCTOR_NAME());
                        father_array.add(resultBean);
                        break;
                    }
                }
            } else {
                UserYuyue.ResultBean resultBean = new UserYuyue.ResultBean();
                resultBean.setBESPOKE_TIME(beans.get(i).getBESPOKE_TIME());
                resultBean.setDOCTOR_NAME(beans.get(i).getDOCTOR_NAME());
                father_array.add(resultBean);
            }
        }
        // 去重
        removeDuplicate(father_array);
        // 排序
        SortResultTool sort = new SortResultTool();
        Collections.sort(father_array, sort);
        for (int j = 0; j < father_array.size(); j++) {
            List<UserYuyue.ResultBean> sonList = new ArrayList<UserYuyue.ResultBean>();
            System.out.println(j + "父==>" + father_array.get(j).getDOCTOR_NAME() + "===" + father_array.get(j).getBESPOKE_TIME());
            String fName = father_array.get(j).getDOCTOR_NAME();
            String fTime = father_array.get(j).getBESPOKE_TIME();
            // 如果发现项目名称 所在的医生和时间名字相同那么把这个item添加到 对应的父组下
            for (int i = 0; i < beans.size(); i++) {
                String itemName = beans.get(i).getITEM_NAME();
                String name = beans.get(i).getDOCTOR_NAME();
                String time = beans.get(i).getBESPOKE_TIME();
                if (name.equals(fName) && time.equals(fTime)) {
                    System.out.println(i + "子==>" + name + "===" + time + "===" + itemName);
                    UserYuyue.ResultBean resultBean = new UserYuyue.ResultBean();
                    resultBean.setDOCTOR_NAME(name);
                    resultBean.setBESPOKE_TIME(time);
                    resultBean.setITEM_NAME(itemName);
                    sonList.add(resultBean);
                    father_array.get(j).setResultBeans(sonList);
                }
            }
        }
    }

    /**
     * 去掉list中对象重复的数据
     * List order not maintained
     **/
    public void removeDuplicate(ArrayList<UserYuyue.ResultBean> arrayList) {
        if (!arrayList.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                for (int j = arrayList.size() - 1; j > i; j--) {
                    if ((arrayList.get(i).getDOCTOR_NAME().equals(arrayList
                            .get(j).getDOCTOR_NAME()))
                            && (arrayList.get(i).getBESPOKE_TIME()
                            .equals(arrayList.get(j).getBESPOKE_TIME()))) {
                        arrayList.remove(j);
                    }
                }
            }
        }
    }

    static class Son_ViewHolder {
        @Bind(R.id.textChild)
        TextView textChild;

        Son_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class Father_ViewHolder {
        @Bind(R.id.textGroup)
        TextView textGroup;
        @Bind(R.id.textGroup_2)
        TextView textGroup2;

        Father_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
