package com.lxb.jyb.util;

//拉取数据的url 

public class HttpConstant {

    /**
     * 服务器地址
     */
    public final static String HOST = "http://api.fxgold.com/";
    /**
     * 日历数据
     */
    public final static String CALENDAR_HOST = HOST + "IndexEventApi?date=";
    /**
     * 日历详情页
     */
    public final static String DETAILS_HOST = "http://api.fxgold.com/IndexEventDetailApi?basicIndexId=";
    /**
     * 财经大事
     */
    public final static String THINGS_HOST = HOST + "FinanceEventApi?date=";
    /**
     * 直播
     */
    public final static String NEWS_HOST = " http://139.196.50.57:8080/api/LiveTestApi?page=";

    /**
     * 新闻接口
     */
    public final static String NEWS_LISTHOST = "http://139.196.50.57:8080/api/NewsListByCategoryApi?category=";
    /**
     * 新闻接口页码
     */
    public final static String NEWS_PAGE = "&page=";
    /**
     * 新闻详情页面webview地址
     */
    public final static String NEWS_WEBHOST = "http://api.fxgold.com/app_news_001.html?source=app&newsid=";
    /**
     * 新闻分类
     */
    public final static String NEWS_CATEGORY = "http://139.196.50.57:8080/api/NewsCategoryApi";
    /**
     * 图片地址
     */
    public final static String BITMAP_HOST = "http://139.196.45.233:8080/jrfxs/FENXISHI_IMG/";
    /**
     * 注册接口
     */
    public static final String REG = "http://139.196.50.57:8090/hongTaoCase/regios?";
    /**
     * 行情接口 http://pull.api.fxgold.com/api/r?products=PMAU,PMHKAUYH
     */
    public static final String HQ_HOST = "http://pull.api.fxgold.com/realtime/products?codes=";

    /**
     * 行情详情页面webview接口
     */
    public static final String HQ_WEBHOST = "http://hq.fxgold.com/realtimeChart?symbol=";
    /**
     * 行情市场接口
     */
    public static final String HQ_SCHOST = "http://pull.api.fxgold.com/realtime/markets/";

    public static final String URE = "http://139.196.50.57:8090/hongTaoCase/checkphone?";

    public static final String REGISTER_VERIFICATION = "http://139.196.50.57:8090/hongTaoCase/checkcode?";
    /**
     * 登陆
     */
    public static final String DISEMBARK = "http://139.196.50.57:8090/hongTaoCase/loginios?";
    /**
     * 修改密码
     */
    public static final String PASSWORK = "http://139.196.50.57:8090/hongTaoCase/modpw?";

    /**
     * K线数据请求接口
     * http://pull.api.fxgold.com/api/h?product=IXEAINUDI&period=1&from=
     * 1444737670025&to=1444737670025
     */
    public static final String K_HISTORY = "http://pull.api.fxgold.com/api/h?product=";
    public static final String K_ADDS = "&from=";
    // 1444737670025&to=1444737670025";
    public static final String END_KLINE = "http://pull.api.fxgold.com/api/r?products=";

    public static final String HEAD = "http://172.16.3.50:8080/business-trade/";

    /**
     * 创建订单(开仓)
     */
    public static final String CREATE_ORDER = HEAD + "order/new";
    /**
     * 修改订单
     */
    public static final String UPDATE_ORDER = HEAD + "order";
    /**
     * 平仓
     */
    public static final String CLOSE_ORDER = HEAD + "order/close";
    /**
     * 查询现有持仓
     */
    public static final String SEL_ORDER = HEAD
            + "order?broker=GMI&account=900171576";
    /**
     * 查询历史订单
     */
    public static final String HISTORY_ORDER = HEAD
            + "order/history?account=900171576&broker=GMI&starttime=2016-04-01&endtime=2016-05-10";
    /**
     * 查询交易品种
     */
    public static final String SEL_SYMBOL = HEAD
            + "broker/symbols?broker=GMI&account=900171576";
    /**
     * 获取账号信息 GET
     */
    public static final String GETUSERINFO = HEAD
            + "account?account=900171576&broker=GMI";
    /*绑定交易账号*/
    public static final String BDMT4_HOST = HEAD + "account";
    /****
     * 获取交易价格
     ****/
    public static final String GETPRICE = HEAD + "broker/price?broker=GMI&account=900171576&symbol=";
}
