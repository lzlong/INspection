package com.li.inspection.constant;

/**
 * Created by long on 17-1-12.
 */

public class Constants {
    public static final String TAG = "Tag";
    public static String judea[] = {"车辆识别代号","发动机型号/号码","车辆品牌/型号","车身颜色","核定载人数","车辆类型","号牌/车辆外观形状","轮胎完好情况","安全带/三角警告牌"};
    public static String judeb[] = {"外廓尺寸","轴数","轴距","整备质量","轮胎规格","侧后部防护装置","车身反光标识和车辆尾部标志板","喷漆","灭火器","行驶记录装置","车内录像监控装置","应急出口/应急锤","乘客门","外部标识/文字","喷漆","标志灯具","报警器","检验合格证明"};
    public static String Gps = "";
    public static String USE_PROPERTY[] = {"01 非营用", "02 公路客运","03 公交客运","04 货运"};
    public static String PLATE_TYPE[] = {"01 大型汽车", "02 小型汽车","16 教练汽车"};
    public static String SERVICE_TYPE[] = {"A 注册登记", "B 变更登记","I 转入业务"};
    public static String VEHICLE_TYPE[] = {"K33 小型轿车", "K16 大型轿车","K32 小型越野客车"};
    public static String VEHICLE_COLOR[] = {"白色", "黑色","红色", "黄色", "绿色"};
    public static String VEHICLE_PN[] = {"1", "2","3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
    public static String HTTP_PATH = "http://192.168.2.150:8090";
//    public static String HTTP_PATH = "http://192.168.2.226:8099";
//    public static String HTTP_PATH = "http://sdkj.kmdns.net:4008";
    public final static String WEBSERVCIE_PATH = "/Inspection/services/TpiWebService?wsdl";
    public static String UPLOADSERVER = "192.168.2.150";
//    public final static String UPLOADSERVER = "192.168.2.226";
//    public final static String UPLOADSERVER = "sdkj.kmdns.net";
    public final static int UPLOADSERVERPORT = 8821;

}
