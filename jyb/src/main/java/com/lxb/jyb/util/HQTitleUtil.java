package com.lxb.jyb.util;

import java.util.ArrayList;

import com.lxb.jyb.bean.HQentity;

public class HQTitleUtil {
    /**
     * 获取默认自选的item
     *
     * @param application
     * @return
     */
    public static ArrayList<HQentity> getDefaultItem() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        // list.add(new HQentity("IXEAINSZZS", "上证综合", 100));
        list.add(new HQentity("IXEAINUDI", "美元指数", 100));
        list.add(new HQentity("IXFXEURUSD", "欧元美元", 10000));
        list.add(new HQentity("IXFXGBPUSD", "英镑美元", 10000));
        return list;
    }

    public static ArrayList<HQentity> getZiXuanItem(ArrayList<String> names) {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.addAll(getQQWH());
        list.addAll(getXHHJ());
        list.addAll(getCOMEX());
        list.addAll(getYY());
        list.addAll(getSPSC());
        list.addAll(getQQGZ());
        list.addAll(getSHJ());
        list.addAll(getTTY());
        list.addAll(getLDJS());
        ArrayList<String> na = new ArrayList<String>();
        ArrayList<HQentity> arrayList = new ArrayList<HQentity>();
        na.addAll(names);
        for (int i = 0; i < na.size(); i++) {
            String string = na.get(i);
            for (int k = 0; k < list.size(); k++) {
                String name = list.get(k).getName();
                if (string.equals(name)) {
                    arrayList.add(list.get(k));
                    break;
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<HQentity> getAllItem() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.addAll(getQQWH());
        list.addAll(getXHHJ());
        list.addAll(getCOMEX());
        list.addAll(getYY());
        list.addAll(getSPSC());
        list.addAll(getQQGZ());
        list.addAll(getSHJ());
        list.addAll(getTTY());
        list.addAll(getLDJS());

        return list;
    }

    /**
     * 生成全球外汇的item
     *
     * @return
     */
    public static ArrayList<HQentity> getQQWH() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("IXFXEURUSD", "欧元美元", 10000));
        list.add(new HQentity("IXFXUSDJPY", "美元日元", 100));
        list.add(new HQentity("IXFXAUDUSD", "澳元美元", 10000));
        list.add(new HQentity("IXFXUSDCAD", "美元加元", 10000));
        list.add(new HQentity("IXFXUSDCHF", "美元瑞郎", 10000));
        list.add(new HQentity("IXFXEURJPY", "欧元日元", 100));
        list.add(new HQentity("IXFXGBPUSD", "英镑美元", 10000));
        list.add(new HQentity("IXFXNZDUSD", "纽元美元", 10000));
        list.add(new HQentity("IXFXAUDJPY", "澳元日元", 100));
        list.add(new HQentity("IXEAINUDI", "美元指数", 100));
        list.add(new HQentity("IXFXEURAUD", "欧元澳元", 10000));
        list.add(new HQentity("IXFXGBPJPY", "英镑日元", 100));
        list.add(new HQentity("IXFXUSDHKD", "美元港币", 10000));
        list.add(new HQentity("IXFXEURCHF", "欧元瑞郎", 10000));
        list.add(new HQentity("IXFXEURGBP", "欧元英镑", 10000));
        list.add(new HQentity("IXFXAUDNZD", "澳元纽元", 10000));
        list.add(new HQentity("IXFXUSDTWD", "美元新台币", 1000));
        list.add(new HQentity("IXFXUSDCNY", "美元人民币", 10000));
        list.add(new HQentity("IXFXAUDCNY", "澳元人民币", 10000));
        list.add(new HQentity("IXFXUSDSGD", "美元坡元", 10000));
        list.add(new HQentity("IXFXEURCAD", "欧元加元", 10000));
        list.add(new HQentity("IXFXEURNOK", "欧元挪威克朗", 10000));
        list.add(new HQentity("IXFXEURSEK", "欧元瑞典克朗", 10000));
        list.add(new HQentity("IXFXGBPAUD", "英镑澳元", 10000));
        list.add(new HQentity("IXFXGBPCAD", "英镑加元", 10000));
        list.add(new HQentity("IXFXGBPCHF", "英镑瑞郎", 10000));
        list.add(new HQentity("IXFXAUDCAD", "澳元加元", 10000));
        list.add(new HQentity("IXFXUSDBRL", "美元巴西雷亚尔", 10000));
        list.add(new HQentity("IXFXUSDDKK", "美元丹麦克朗", 10000));
        list.add(new HQentity("IXFXUSDINR", "美元印度卢比", 100));
        list.add(new HQentity("IXFXUSDKRW", "美元韩元", 100));
        list.add(new HQentity("IXFXUSDMXN", "美元墨西哥元", 10000));
        list.add(new HQentity("IXFXUSDNOK", "美元挪威克朗", 10000));
        list.add(new HQentity("IXFXUSDPLN", "美元波兰兹罗提", 10000));
        list.add(new HQentity("IXFXUSDRUB", "美元俄罗斯卢布", 10000));
        list.add(new HQentity("IXFXUSDSEK", "美元瑞典克朗", 10000));
        list.add(new HQentity("IXFXUSDTHB", "美元泰铢", 10000));
        return list;
    }

    /**
     * 获取COMEX
     *
     * @return
     */
    public static ArrayList<HQentity> getCOMEX() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();

        list.add(new HQentity("IXCMGCA0", "黄金连续", 100));
        list.add(new HQentity("CMGCG0", "美黄金02", 100));
        list.add(new HQentity("CMGCJ0", "美黄金04", 100));
        list.add(new HQentity("CMGCK0", "美黄金05", 100));
        list.add(new HQentity("CMGCM0", "美黄金06", 100));
        list.add(new HQentity("CMGCQ0", "美黄金08", 100));
        list.add(new HQentity("CMGCV0", "美黄金10", 100));
        list.add(new HQentity("CMGCZ0", "美黄金12", 100));
        list.add(new HQentity("IXCMSIA0", "白银连续", 100));
        list.add(new HQentity("CMSIF0", "美白银01", 100));
        list.add(new HQentity("CMSIH0", "美白银03", 100));
        list.add(new HQentity("CMSIJ0", "美白银04", 100));
        list.add(new HQentity("CMSIK0", "美白银05", 100));
        list.add(new HQentity("CMSIM0", "美白银06", 100));
        list.add(new HQentity("CMSIN0", "美白银07", 100));
        list.add(new HQentity("CMSIU0", "美白银09", 100));
        list.add(new HQentity("CMSIZ0", "美白银12", 100));
        list.add(new HQentity("IXNEPAA0", "钯金连续", 100));
        list.add(new HQentity("IXNEPAH0", "钯金03月", 100));
        list.add(new HQentity("IXNEPAM0", "钯金06月", 100));
        list.add(new HQentity("IXNEPAU0", "钯金09月", 100));
        list.add(new HQentity("IXNEPAZ0", "钯金12月", 100));
        list.add(new HQentity("IXNEPLA0", "铂金连续", 100));
        list.add(new HQentity("IXNEPLF0", "铂金01月", 100));
        list.add(new HQentity("IXNEPLJ0", "铂金04月", 100));
        list.add(new HQentity("IXNEPLN0", "铂金07月", 100));
        list.add(new HQentity("IXNEPLV0", "铂金10月", 100));
        return list;
    }

    /**
     * 生成现货黄金
     *
     * @return
     */
    public static ArrayList<HQentity> getXHHJ() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("OSCNYAGG", "人民币白银 kg", 100));
        list.add(new HQentity("OSCNYAUG", "人民币黄金 g", 100));
        list.add(new HQentity("PMAU", "黄金现货", 100));
        list.add(new HQentity("PMAG", "白银现货", 100));
        list.add(new HQentity("PMAP", "铂金现货", 100));
        list.add(new HQentity("PMPD", "钯金现货", 100));
        list.add(new HQentity("PMHKAULD", "港伦敦金", 100));
        list.add(new HQentity("PMHKAGLD", "港伦敦银", 100));
        list.add(new HQentity("OSHKG", "现货港金", 100));
        list.add(new HQentity("OSTWGD", "黄金台两", 100));
        list.add(new HQentity("PMHKAUJC", "金昌港金", 100));
        list.add(new HQentity("PMHKAUYH", "英皇港金", 100));
        return list;
    }

    /**
     * 获取原油item
     *
     * @return
     */
    public static ArrayList<HQentity> getYY() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("NECLA0", "美原油连续", 100));
        list.add(new HQentity("NECLF0", "美原油01", 100));
        list.add(new HQentity("NECLG0", "美原油02", 100));
        list.add(new HQentity("NECLH0", "美原油03", 100));
        list.add(new HQentity("NECLJ0", "美原油04", 100));
        list.add(new HQentity("NECLK0", "美原油05", 100));
        list.add(new HQentity("NECLM0", "美原油06", 100));
        list.add(new HQentity("NECLN0", "美原油07", 100));
        list.add(new HQentity("NECLQ0", "美原油08", 100));
        list.add(new HQentity("NECLU0", "美原油09", 100));
        list.add(new HQentity("NECLV0", "美原油10", 100));
        list.add(new HQentity("NECLX0", "美原油11", 100));
        list.add(new HQentity("NECLZ0", "美原油12", 100));
        list.add(new HQentity("IPBRNA0", "布伦特原油连续", 100));
        list.add(new HQentity("IPBRNF0", "布伦特原油 01", 100));
        list.add(new HQentity("IPBRNG0", "布伦特原油 02", 100));
        list.add(new HQentity("IPBRNH0", "布伦特原油 03", 100));
        list.add(new HQentity("IPBRNJ0", "布伦特原油 04", 100));
        list.add(new HQentity("IPBRNK0", "布伦特原油 05", 100));
        list.add(new HQentity("IPBRNM0", "布伦特原油 06", 100));
        list.add(new HQentity("IPBRNN0", "布伦特原油 07", 100));
        list.add(new HQentity("IPBRNQ0", "布伦特原油 08", 100));
        list.add(new HQentity("IPBRNU0", "布伦特原油 09", 100));
        list.add(new HQentity("IPBRNV0", "布伦特原油 10", 100));
        list.add(new HQentity("IPBRNX0", "布伦特原油 11", 100));
        list.add(new HQentity("IPBRNZ0", "布伦特原油 12", 100));
        return list;
    }

    /**
     * 生成商品市场的item code
     *
     * @return
     */
    public static ArrayList<HQentity> getSPSC() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("IXCOZCA0", "美玉米连续", 100));
        list.add(new HQentity("IXCOZCH6", "美玉米1603", 100));
        list.add(new HQentity("IXCOZCK5", "美玉米1505", 100));
        list.add(new HQentity("IXCOZCN5", "美玉米1507", 100));
        list.add(new HQentity("IXCOZCU5", "美玉米1509", 100));
        list.add(new HQentity("IXCOZCZ5", "美玉米1512", 100));
        list.add(new HQentity("IXCOZLA0", "美豆油连续", 100));
        list.add(new HQentity("IXCOZLF6", "美豆油1601", 100));
        list.add(new HQentity("IXCOZLH6", "美豆油1603", 100));
        list.add(new HQentity("IXCOZLK5", "美豆油1505", 100));
        list.add(new HQentity("IXCOZLN5", "美豆油1507", 100));
        list.add(new HQentity("IXCOZLQ5", "美豆油1508", 100));
        list.add(new HQentity("IXCOZLU5", "美豆油1509", 100));
        list.add(new HQentity("IXCOZLV5", "美豆油1510", 100));
        list.add(new HQentity("IXCOZLZ5", "美豆油1512", 100));
        list.add(new HQentity("IXCOZMA0", "美豆粕连续", 100));
        list.add(new HQentity("IXCOZMF6", "美豆粕1601", 100));
        list.add(new HQentity("IXCOZMH6", "美豆粕1603", 100));
        list.add(new HQentity("IXCOZMK5", "美豆粕1505", 100));
        list.add(new HQentity("IXCOZMN5", "美豆粕1507", 100));
        list.add(new HQentity("IXCOZMQ5", "美豆粕1508", 100));
        list.add(new HQentity("IXCOZMU5", "美豆粕1509", 100));
        list.add(new HQentity("IXCOZMV5", "美豆粕1510", 100));
        list.add(new HQentity("IXCOZMZ5", "美豆粕1512", 100));
        list.add(new HQentity("IXCOZOA0", "美燕麦连续", 100));
        list.add(new HQentity("IXCOZOH6", "美燕麦1603", 100));
        list.add(new HQentity("IXCOZOK5", "美燕麦1505", 100));
        list.add(new HQentity("IXCOZON5", "美燕麦1507", 100));
        list.add(new HQentity("IXCOZOU5", "美燕麦1509", 100));
        list.add(new HQentity("IXCOZOZ5", "美燕麦1512", 100));
        list.add(new HQentity("IXCOZRA0", "美稻米连续", 100));
        list.add(new HQentity("IXCOZRF6", "美稻米1601", 100));
        list.add(new HQentity("IXCOZRH6", "美稻米1603", 100));
        list.add(new HQentity("IXCOZRK5", "美稻米1505", 100));
        list.add(new HQentity("IXCOZRN5", "美稻米1507", 100));
        list.add(new HQentity("IXCOZRU5", "美稻米1509", 100));
        list.add(new HQentity("IXCOZRX5", "美稻米1511", 100));
        list.add(new HQentity("IXCOZSA0", "美大豆连续", 100));
        list.add(new HQentity("IXCOZSF6", "美大豆1601", 100));
        list.add(new HQentity("IXCOZSH6", "美大豆1603", 100));
        list.add(new HQentity("IXCOZSK5", "美大豆1505", 100));
        list.add(new HQentity("IXCOZSN5", "美大豆1507", 100));
        list.add(new HQentity("IXCOZSQ5", "美大豆1508", 100));
        list.add(new HQentity("IXCOZSU5", "美大豆1509", 100));
        list.add(new HQentity("IXCOZSX5", "美大豆1511", 100));
        list.add(new HQentity("IXCOZWA0", "美小麦连续", 100));
        list.add(new HQentity("IXCOZWH6", "美小麦1603", 100));
        list.add(new HQentity("IXCOZWK5", "美小麦1505", 100));
        list.add(new HQentity("IXCOZWN5", "美小麦1507", 100));
        list.add(new HQentity("IXCOZWU5", "美小麦1509", 100));
        list.add(new HQentity("IXCOZWZ5", "美小麦1512", 100));
        list.add(new HQentity("IXEAIECTA0", "美棉花连", 100));
        list.add(new HQentity("IXIECCA0", "美可可连续", 100));
        list.add(new HQentity("IXIECCK5", "美可可1505", 100));
        list.add(new HQentity("IXIECCN5", "美可可1507", 100));
        list.add(new HQentity("IXIECCU5", "美可可1509", 100));
        list.add(new HQentity("IXIECCZ5", "美可可1512", 100));
        list.add(new HQentity("IXIECTA0", "美棉花2号连续", 100));
        list.add(new HQentity("IXIECTH6", "美棉花2号 1603", 100));
        list.add(new HQentity("IXIECTK5", "美棉花2号 1505", 100));
        list.add(new HQentity("IXIECTN5", "美棉花2号 1507", 100));
        list.add(new HQentity("IXIECTV5", "美棉花2号 1510", 100));
        list.add(new HQentity("IXIECTZ5", "美棉花2号 1512", 100));
        list.add(new HQentity("IXIEKCA0", "美咖啡连续", 100));
        list.add(new HQentity("IXIEKCK5", "美咖啡1505", 100));
        list.add(new HQentity("IXIEKCN5", "美咖啡1507", 100));
        list.add(new HQentity("IXIEKCU5", "美咖啡1509", 100));
        list.add(new HQentity("IXIEKCZ5", "美咖啡1512", 100));
        list.add(new HQentity("IXIEOJA0", "美橙汁连续", 100));
        list.add(new HQentity("IXIEOJF6", "美橙汁1601", 100));
        list.add(new HQentity("IXIEOJH6", "美橙汁1603", 100));
        list.add(new HQentity("IXIEOJK5", "美橙汁1505", 100));
        list.add(new HQentity("IXIEOJN5", "美橙汁1507", 100));
        list.add(new HQentity("IXIEOJU5", "美橙汁1509", 100));
        list.add(new HQentity("IXIEOJX5", "美橙汁1511", 100));
        list.add(new HQentity("IXEAIESBA0", "11号糖连", 100));
        list.add(new HQentity("IXIESBA0", "11号糖连续", 100));
        list.add(new HQentity("IXIESBH6", "11号糖1603", 100));
        list.add(new HQentity("IXIESBK5", "11号糖1505", 100));
        list.add(new HQentity("IXIESBN5", "11号糖1507", 100));
        list.add(new HQentity("IXIESBV5", "11号糖1510", 100));

        return list;
    }

    /**
     * 生成全球股指item 的code
     *
     * @return
     */
    public static ArrayList<HQentity> getQQGZ() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("IXIXSZCZ", "深证成指", 100));
        list.add(new HQentity("IXIXSZZS", "上证综合", 100));
        list.add(new HQentity("IXIXSH000300", "沪深300", 100));
        list.add(new HQentity("IXIXDJIA", "美国道指", 100));
        list.add(new HQentity("IXIXSPX", "标普500", 100));
        list.add(new HQentity("IXIXNDX", "美国纳指", 100));
        list.add(new HQentity("IXIXHSCEI", "恒生国企", 100));
        list.add(new HQentity("IXEAINHSI", "恒生指数", 100));
        list.add(new HQentity("IXIXXIN0", "富时中国 50", 100));
        list.add(new HQentity("IXIXXIN9", "富时中国 A50", 100));
        list.add(new HQentity("IXIXGDAXI", "德国 DAX30", 100));
        list.add(new HQentity("IXIXFTSE", "英国富时 100", 100));
        list.add(new HQentity("IXIXFCHI", "法国 CAC40", 100));
        list.add(new HQentity("IXIXFMIB", "意大利富时 MIB", 100));
        list.add(new HQentity("IXIXEF3X", "富时欧洲先驱 300", 100));
        list.add(new HQentity("IXIXEF80", "富时欧洲先驱 80", 100));
        list.add(new HQentity("IXIXEFC1", "富时欧洲先驱 100", 100));
        list.add(new HQentity("IXIXFTSTI", "富时海峡时报", 100));
        list.add(new HQentity("IXIXH25", "芬兰赫尔辛基 25", 100));
        list.add(new HQentity("IXIXN225", "日经225", 100));
        list.add(new HQentity("IXIXNZ50", "新西兰50", 100));
        list.add(new HQentity("IXEAINTWII", "台湾加权", 100));
        list.add(new HQentity("IXIXTW50", "台湾 50 指数", 100));
        list.add(new HQentity("IXIXAEX", "荷兰 AEX 指数", 100));
        list.add(new HQentity("IXIXAORD", "澳大利亚普通股", 100));
        list.add(new HQentity("IXIXASE", "希腊雅典 ASE", 100));
        list.add(new HQentity("IXIXATX", "奥地利 ATX", 100));
        list.add(new HQentity("IXIXBEL20", "比利时 20", 100));
        list.add(new HQentity("IXIXC20", "丹麦哥本哈根 20", 100));
        list.add(new HQentity("IXIXIBEX", "西班牙 IBEX35", 100));
        list.add(new HQentity("IXIXIBOVESPA", "巴西 IBOVESPA", 100));
        list.add(new HQentity("IXIXIPI", "冰岛全股", 100));
        list.add(new HQentity("IXIXISEQ", "爱尔兰综合", 100));
        list.add(new HQentity("IXIXKLSE", "马来西亚 KLCI", 100));
        list.add(new HQentity("IXIXKS11", "韩国 KOSPI", 100));
        list.add(new HQentity("IXIXKSP2", "韩国 KOSPI200", 100));
        list.add(new HQentity("IXIXPCOMP", "菲律宾证券", 100));
        list.add(new HQentity("IXIXPSI20", "葡萄牙 PSI20", 100));
        list.add(new HQentity("IXIXRTS", "俄罗斯 RTS", 100));
        list.add(new HQentity("IXIXS30", "瑞典斯德哥尔摩30", 100));
        list.add(new HQentity("IXIXSENSEX", "印度 SENSEX30", 100));
        list.add(new HQentity("IXIXSET", "泰国SET指数", 100));
        list.add(new HQentity("IXIXSMI", "瑞士SMI指数", 100));
        list.add(new HQentity("IXIXSX5E", "欧洲斯托克50", 100));
        list.add(new HQentity("IXIXSXXP", "欧洲斯托克600", 100));
        list.add(new HQentity("IXIXTSX", "加拿大 S&PTSX", 100));
        list.add(new HQentity("IXIXXJO", "标普澳证200", 100));
        return list;
    }

    /**
     * 生成上海金item code
     *
     * @return
     */
    public static ArrayList<HQentity> getSHJ() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("SGAgT+D", "沪白银T+D", 100));
        list.add(new HQentity("SGAuT+D", "沪黄金T+D", 100));
        list.add(new HQentity("SGmAuT+D", "沪m黄金 T+D", 100));
        list.add(new HQentity("SGAu100g", "沪黄金100G", 100));
        list.add(new HQentity("SGAu9995", "沪黄金9995", 100));
        list.add(new HQentity("SGAu9999", "沪黄金9999", 100));
        list.add(new HQentity("SGiAu100g", "沪i黄金 100g", 100));
        list.add(new HQentity("SGiAu9999", "沪i黄金 9999", 100));
        list.add(new HQentity("SGPT9995", "沪铂金9995", 100));
        return list;
    }

    /**
     * 生成天通银item code
     *
     * @return
     */
    public static ArrayList<HQentity> getTTY() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("TJAG", "天津白银", 100));
        list.add(new HQentity("TJAG30KG", "天津白银 30KG", 100));
        list.add(new HQentity("TJMAG", "天津迷你白银", 100));
        list.add(new HQentity("TJAP", "天津铂金", 100));
        list.add(new HQentity("TJMAP", "天津迷你铂金", 100));
        list.add(new HQentity("TJNI", "天津钯金", 100));
        list.add(new HQentity("TJMPD", "天津迷你钯金", 100));
        list.add(new HQentity("TJNI", "天津镍", 100));
        list.add(new HQentity("TJMNI", "天津迷你镍", 100));
        list.add(new HQentity("TJCU", "天津现货铜", 100));
        list.add(new HQentity("TJCU1T", "天津现货铜 1T", 100));
        list.add(new HQentity("TJAL", "天津现货铝", 100));
        list.add(new HQentity("TJAL1T", "天津现货铝 1T", 100));
        return list;
    }

    /**
     * 生成伦敦金属item code
     *
     * @return
     */
    public static ArrayList<HQentity> getLDJS() {
        ArrayList<HQentity> list = new ArrayList<HQentity>();
        list.add(new HQentity("IXLEAHD3M", "LME铝03", 100));
        list.add(new HQentity("IXLECAD3M", "LME铜03", 100));
        list.add(new HQentity("IXLENAD3M", "LME北美铝合金 03", 100));
        list.add(new HQentity("IXLENID3M", "LME镍03", 100));
        list.add(new HQentity("IXLEPBD3M", "LME铅03", 100));
        list.add(new HQentity("IXLESND3M", "LME锡03", 100));
        list.add(new HQentity("IXLEZSD3M", "LME锌03", 100));
        list.add(new HQentity("LEAAD3M", "LME铝合金 03(电子)", 100));
        list.add(new HQentity("LEAHD3M", "LME铝 03(电子)", 100));
        list.add(new HQentity("LECAD3M", "LME铜 03(电子)", 100));
        list.add(new HQentity("LECOD3M", "LME钴 03(电子)", 100));
        list.add(new HQentity("LEMOD3M", "LME钼 03(电子)", 100));
        list.add(new HQentity("LENID3M", "LME镍 03(电子)", 100));
        list.add(new HQentity("LEPBD3M", "LME铅 03(电子)", 100));
        list.add(new HQentity("LESND3M", "LME锡 03(电子)", 100));
        list.add(new HQentity("LEZSD3M", "LME锌 03(电子)", 100));
        return list;
    }
}
