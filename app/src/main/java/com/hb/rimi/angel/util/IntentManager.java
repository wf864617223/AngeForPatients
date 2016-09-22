package com.hb.rimi.angel.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hb.rimi.angel.activity.MainActivity;
import com.hb.rimi.angel.activity.home.AddUserInfoActivity;
import com.hb.rimi.angel.activity.home.AppointmentActivity;
import com.hb.rimi.angel.activity.home.AppointmentPayActivity;
import com.hb.rimi.angel.activity.home.BAppointmentPayActivity;
import com.hb.rimi.angel.activity.home.BillPaymentActivity;
import com.hb.rimi.angel.activity.home.CheckPaymentActivity;
import com.hb.rimi.angel.activity.home.DesDetailActivity;
import com.hb.rimi.angel.activity.home.DoctorDetailActivity;
import com.hb.rimi.angel.activity.home.DoctorListActivity;
import com.hb.rimi.angel.activity.home.ElectronicReportActivity;
import com.hb.rimi.angel.activity.home.HistoryCheckPaymentActivity;
import com.hb.rimi.angel.activity.home.MedicalReminderActivity;
import com.hb.rimi.angel.activity.home.MedicalVoucherActivity;
import com.hb.rimi.angel.activity.home.BOptionalCheckActivity;
import com.hb.rimi.angel.activity.home.VisitListActivity;
import com.hb.rimi.angel.activity.home.VisitRemindActivity;
import com.hb.rimi.angel.activity.marker.ShopCarActivity;
import com.hb.rimi.angel.activity.mine.ForgetSoneActivity;
import com.hb.rimi.angel.activity.mine.ForgetStwoActivity;
import com.hb.rimi.angel.activity.mine.LoginActivity;
import com.hb.rimi.angel.activity.mine.RegisterActivity;
import com.hb.rimi.angel.activity.pay.PayActivity;

/**
 * 在这个类中集中设置你要跳转到的目标Activty
 * 函数命名方法可以按照toXxxxx(参数。。。); 参自定
 * Created by Administrator on 2016/5/14.
 * 构造函数里面的context 可以通过Application里面实例化类得到
 */
public class IntentManager {
    private static volatile IntentManager intentManager;
    private Context context;

    public IntentManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public static IntentManager getInstance(Context context) {
        if (intentManager == null) {
            synchronized (IntentManager.class) {
                if (intentManager == null) {
                    return new IntentManager(context);
                }
            }
        }
        return intentManager;
    }

    /**
     * MainActivity
     *
     * @param bundle 代表要传递的参数
     */
    public void toMainActivity(Bundle bundle) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    /**
     * LoginActivity
     * 登录界面
     *
     * @param bundle 代表要传递的参数
     */
    public void toLoginActivity(Bundle bundle) {

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    } /**
     * LoginActivity
     * 安全退出
     *
     * @param bundle 代表要传递的参数
     */
    public void toOutLoginActivity(Bundle bundle) {


        Intent logoutIntent = new Intent(context,
                LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(logoutIntent);

//        context.finish();

//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        if (bundle != null && bundle.size() > 0) {
//            intent.putExtras(bundle);
//        }
//        context.startActivity(intent);
    }

    /**
     * RegisterActivity
     * 注册界面
     *
     * @param bundle 代表要传递的参数
     */
    public void toRegisterActivity(Bundle bundle) {

        Intent intent = new Intent(context, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * ForgetSoneActivity
     * 忘记密码1
     *
     * @param bundle 代表要传递的参数
     */
    public void toForgetSoneActivity(Bundle bundle) {

        Intent intent = new Intent(context, ForgetSoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * ForgetStwoActivity
     * 忘记密码2
     *
     * @param bundle 代表要传递的参数
     */
    public void toForgetStwoActivity(Bundle bundle) {

        Intent intent = new Intent(context, ForgetStwoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * AppointmentActivity
     * 预约挂号
     *
     * @param bundle 代表要传递的参数
     */
    public void toAppointmentActivity(Bundle bundle) {

        Intent intent = new Intent(context, AppointmentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * DoctorListActivity
     * 医生列表
     *
     * @param bundle 代表要传递的参数
     */
    public void toDoctorListListActivity(Bundle bundle) {

        Intent intent = new Intent(context, DoctorListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public void toVisitRemindActivity(Bundle bundle) {
        Intent intent = new Intent(context, VisitRemindActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * DoctorDetailActivity
     * 医生详细
     *
     * @param bundle 代表要传递的参数
     */
    public void toDoctorDetailActivity(Bundle bundle) {

        Intent intent = new Intent(context, DoctorDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * AppointmentPayActivity
     * 预约支付
     *
     * @param bundle 代表要传递的参数
     */
    public void toAppointmentPayActivity(Bundle bundle) {

        Intent intent = new Intent(context, AppointmentPayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * CheckPaymentActivity
     * 检查缴费
     *
     * @param bundle 代表要传递的参数
     */
    public void toCheckPaymentActivity(Bundle bundle) {

        Intent intent = new Intent(context, CheckPaymentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * HistoryCheckPaymentActivity
     * 历史检查缴费
     *
     * @param bundle 代表要传递的参数
     */
    public void toHistoryCheckPaymentActivity(Bundle bundle) {

        Intent intent = new Intent(context, HistoryCheckPaymentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * MedicalVoucherActivity
     * 就诊凭证
     *
     * @param bundle 代表要传递的参数
     */
    public void toMedicalVoucherActivity(Bundle bundle) {

        Intent intent = new Intent(context, MedicalVoucherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * BillPaymentActivity
     * 缴费支付
     *
     * @param bundle 代表要传递的参数
     */
    public void toBillPaymentActivity(Bundle bundle) {

        Intent intent = new Intent(context, BillPaymentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    /**
     * BOptionalCheckActivity
     * B超预约
     *
     * @param bundle 代表要传递的参数
     */
    public void toBOptionalCheckActivity(Bundle bundle) {

        Intent intent = new Intent(context, BOptionalCheckActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * BAppointmentPayActivity
     * B超预约支付
     *
     * @param bundle 代表要传递的参数
     */
    public void toBAppointmentPayActivity(Bundle bundle) {

        Intent intent = new Intent(context, BAppointmentPayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 增加会员资料
     *
     * @param bundle
     */
    public void toAddUserInfoActivity(Bundle bundle) {

        Intent intent = new Intent(context, AddUserInfoActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * MedicalReminderActivity
     * 就诊提醒
     *
     * @param bundle 代表要传递的参数
     */
    public void toMedicalReminderActivity(Bundle bundle) {

        Intent intent = new Intent(context, MedicalReminderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public void toElectorivRecoptActivity(Bundle bundle) {

        Intent intent = new Intent(context, ElectronicReportActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * VisitListActivity
     * 就诊列表
     *
     * @param bundle 代表要传递的参数
     */
    public void toVisitListActivity(Bundle bundle) {

        Intent intent = new Intent(context, VisitListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * PayActivity
     * 支付跳转
     *
     * @param bundle 代表要传递的参数
     */
    public void toPayActivity(Bundle bundle) {

        Intent intent = new Intent(context, PayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    /**
     * DesDetailActivity
     * 更多详情
     *
     * @param bundle 代表要传递的参数
     */
    public void toDesDetailActivity(Bundle bundle) {

        Intent intent = new Intent(context, DesDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    /**
     * ShopCarActivity
     * 到购物车
     *
     * @param bundle 代表要传递的参数
     */
    public void toShopCarActivity(Bundle bundle) {

        Intent intent = new Intent(context, ShopCarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
