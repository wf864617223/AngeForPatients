package com.hb.rimi.angel.contanst;

/**
 * 接口常量
 * Created by rimi on 2016/6/2.
 */
public class HttpContanst {
//    http://do.rimiedu.com/angel/
//    http://117.172.47.171:1234/
    //客户信息及会员卡信息
    public static final String USER_INFO = "http://61.139.124.246:90/Customer/CustomerMeans.aspx?action=GetCustomer";
    //变更电话
    public static final String CHANGE_PHONE = "http://61.139.124.246:90/Customer/CustomerInfo.aspx?action=ChangeMobile";
    //修改密码
    public static final String CHANGE_PWD = "http://61.139.124.246:90/Customer/CustomerInfo.aspx?action=Update";
    //客服专员电话
    public static final String CUSTOM_PHONE = "http://app.cdangel.com/api/system/query?all=false&property=contact_telephone";
    //首页顶部三张幻灯片
    public static final String HOME_TOP_BANNER = "http://app.cdangel.com/api/system/query?all=false&property=app_banner";
    //获取部门列表
    public static final String DEPARTYMENT_LIST = "http://61.139.124.246:90/his/HisAppRegister.ashx?method=dept";
    //TODO 获取医生列表废弃
    public static final String DEPARTYMENT_DOCTOR_LIST = "http://61.139.124.246:90/his/HisAppRegister.ashx?method=doctor&dept_id=";
    //获取医生列表新                                         http://61.139.124.246:90/Customer/Doctor.aspx?action=GetDoctorList
//                                                              http://61.139.124.246:90/Customer/Doctor.aspx?action=GetList&subject=儿科
    public static final String DEPARTYMENT_DOCTOR_LIST_NEW = "http://61.139.124.246:90/Customer/Doctor.aspx";
    //医生个人信息 old=http://61.139.124.246:90/Customer/Doctor.aspx
    public static final String DEPARTYMENT_DOCTOR_DETAIL = "http://61.139.124.246:90/Customer/Doctor.aspx";
    //电子报告列表
    public static final String ELECTROIC_REPORT = "http://61.139.124.246:90/his/HisReport.ashx?method=cList";
    //客户报告详情接口
    public static final String REPORT_INFO = "http://61.139.124.246:90/his/HisReport.ashx?method=info";
    //意见反馈接口
    public static final String ADVICE_POST = "http://app.cdangel.com/api/complaint/user/add";
    //就诊列表
    public static final String VISIT_LIST = "http://61.139.124.246:90/his/HisOrder.ashx";
    //生成订单号
    public static final String CREATE_ORDERNO = "http://61.139.124.246:90/his/HisOrder.ashx";
    //会员预约列表
    public static final String USER_YUYUE_INFO = "http://61.139.124.246:90/his/HisOrder.ashx?method=Mzyy";
    //挂号列表
    public static final String USER_REGISTER_INFO = "http://61.139.124.246:90/his/HisOrder.ashx?method=Reginfo";
    //增加客户资料
    public static final String ADD_USER_INFO = "http://61.139.124.246:90/Customer/CustomerAdd.aspx";
    //根据医生编码获取医生一周的预约信息
    public static final String DOCTOR_WEEK = "http://61.139.124.246:90/Customer/Doctor.aspx";
    //根据检查项目获取一周的列表和数量
    public static final String PROJECT_WEEK = "http://61.139.124.246:90/Customer/Project.aspx";
    //就诊凭证
    public static final String MEDICAL_VOUCHER = "http://app.cdangel.com/api/order/product/list";
    //临时就诊卡号生成接口
    public static final String TEMP_ICNO = "http://app.cdangel.com/api/user/generate/icno";
    //系统配置数据接口
    public static final String SYS_DATA = "http://app.cdangel.com/api/system/query?all=false";
    //服务器地址
    public static final String SERVER_ADD = "http://app.cdangel.com";
    //医生挂号 快捷支付   /api/order/product/{type}/quick
    public static final String CREATE_ORDER_DOCTOR = "http://app.cdangel.com/api/order/product/register/quick";
    //商城订单 生成订单
    public static final String CREATE_ORDER_SHOP = "http://app.cdangel.com/api/order/product/store/add";
    //检查预约 生成订单
    public static final String CREATE_ORDER_CHECK = "http://app.cdangel.com/api/order/product/appointment/quick";
    //门诊缴费 生成订单
    public static final String CREATE_ORDER_OUTPATIENT = "http://app.cdangel.com/api/order/product/payment/quick";
    //付费订单列表
    //public static final String PAY_ORDER_LIST = "http://do.rimiedu.com/angel/api/order/product/list?";
    //更新
    public static final String UPDATE_URL = "http://app.cdangel.com/checkversioncode.json";
    //登录接口
    public static String LOGIN_URL = "http://61.139.124.246:90/Customer/CustomerInfo.aspx?action=Login";
    //发送验证码
    public static String SEND_CODE = "http://app.cdangel.com/api/phonecode/send";
    //校验验证码
    public static String CHECK_CODE = "http://app.cdangel.com/api/phonecode/validation";
    //注册接口
    public static String REGISTER_URL = "http://61.139.124.246:90/Customer/CustomerInfo.aspx?action=Register";
    //未付费订单列表
    public static String UNPAID_ORDER_LIST = "http://61.139.124.246:90/his/HisOrder.ashx";
    //所有消费处方列表
    public static String ALL_CONSUMER_PRESCRIPTIONS_LIST = "http://61.139.124.246:90/his/HisOrder.ashx";
    //可选检查项
    public static String OPTIONAL_CHECK_ITEM = "http://61.139.124.246:90/his/HisAppRegister.ashx?method=item";
    //患者会话token信息同步接口
    public static String SESSION_SYNC = "http://app.cdangel.com/api/user/token/session";
    //获取医生挂号费用
    public static String DOCTOR_COST = "http://61.139.124.246:90/his/HisAppRegister.ashx";
    //就诊提醒 - B超检查信息 "action", "GetList"
    public static String REMIND_BSUPER = "http://61.139.124.246:90/Customer/LineUp.aspx";
    //创建环信账号
    public static String CREATE_HY_USERNAME = "http://app.cdangel.com/api/user/token/android";
    //查询环信账号
    public static String SELECT_HY_USERNAME = "http://app.cdangel.com/api/user/token/easemob?token=";
    //维护末次月经
    public static String UPDATE_LAST_MENSES = "http://61.139.124.246:90/Customer/CustomerUpdate.aspx?action=Update";
    //从后台获取ICNO
    public static String LOCAT_ICNO = "http://app.cdangel.com/api/user/information?token=";
    //获取环信扩展群组列表
    public static String GROUPCHAT = "http://app.cdangel.com/api/groupchat/list?token=";
    //付费订单列表接口13
    public static final String PAY_ORDER_LIST = "http://app.cdangel.com/api/order/product/list?";
    //是否有新消息
    public static final String HAS_NEW_MESSAGE = "http://app.cdangel.com/api/user/message/status?";
    //消息列表
    public static final String MESSAGE_LIST = "http://app.cdangel.com/api/user/message?";
    //删除消息
    public static final String DELETE_MESSAGE = "http://app.cdangel.com/api/user/message/delete";
    //合并订单
    public static final String NEW_ORDERING = "http://app.cdangel.com/api/order/product/create?";
    //商品种类列表查询
    public static final String BUS_STYLE = "http://app.cdangel.com/api/category/getCategoryList?token=";
    //商品列表条件查询查询
    public static final String BUS_LIST = "http://app.cdangel.com/api/product/getProductList?token=";
    //商品加入/移除购物车
    public static final String BUS_ADD_REMOVE = "http://app.cdangel.com/terminal/patient/register?token=";
    //购物车商品列表
    public static final String CAR_BUS_LIST = "http://app.cdangel.com/terminal/patient/register";
    //商品确认下单
    public static final String BUS_ORDERB = "http://app.cdangel.com/terminal/patient/register";
    //商品详情
    public static final String BUS_INFO = "http://app.cdangel.com/api/product/getProductDetail?productId=";
    //付费订单列表接口15
    public static final String PAY_ORDER_TWO_LIST = "http://app.cdangel.com/api/order/pay/list?token=";
    //商城订单 快速支付
    public static final String MARKER_ORDER_QUICK = "http://app.cdangel.com/api/order/product/store/quick";
    //订单重新支付（打包）
    public static final String ORDER_REPAY= "http://app.cdangel.com/api/order/pay/repay";
    //获取CRM未交费的预约客户
    public static final String OPTIONAL_CRM_APP= "http://61.139.124.246:90/Customer/AppointmentInfo.ashx?action=get";
}
