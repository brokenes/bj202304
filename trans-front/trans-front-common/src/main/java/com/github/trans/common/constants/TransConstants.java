package com.github.trans.common.constants;

public class TransConstants {

    public  static final String IP_REGEXP = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";


    public static final String URL_REGEXP = "(https?)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";


    public static final String DECIMAL_REGEXP = "^[1-9][0-9]*(\\.[0-9]{1,2})?$";


    public static final String DATE_FORMAT_REGEXP = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(23|24|25|26|[0-1]\\d):[0-5]\\d:[0-5]\\d$";


    public static final String PAYMENT_ORDER_MODEL = "payment_order_model";

}
