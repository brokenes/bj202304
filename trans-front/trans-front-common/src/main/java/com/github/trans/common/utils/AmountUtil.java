package com.github.trans.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/***
 * 
 * 金额
 * 
 * 如果需要精确计算，必须用String来够造BigDecimal！ ！！ Java里面的商业计算，不能用float和double，因为他们无法
 * 进行精确计算。 但是Java的设计者给编程人员提供了一个很有用的类BigDecimal， 他可以完善float和double类无法进行精确计算的缺憾。
 * BigDecimal类位于java.maths类包下。 它的构造函数很多，最常用的: BigDecimal(double val)
 * BigDecimal(String str) BigDecimal(BigInteger val) BigDecimal(BigInteger
 * unscaledVal, int scale)
 *
 *
 */
public class AmountUtil {

	/***
	 * 保留2位小数 四舍五入
	 * 
	 * @param
	 * @return 返回一个double类型的2位小数
	 */
	public static Double get2Double(Double doubleVal, int scale) {
		if (null == doubleVal) {
			doubleVal = new Double(0);
		}
		return new BigDecimal(doubleVal).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/***
	 * 格式化Double类型并保留scale位小数 四舍五入
	 * 
	 * @param doubleVal
	 * @param scale
	 *            scale必须为大于0的正整数，不能等于0
	 * @return
	 */
	public static String formatBy2Scale(Double doubleVal, int scale) {
		if (null == doubleVal) {
			doubleVal = new Double(0);
		}
		StringBuffer sbStr = new StringBuffer("0.");
		for (int i = 0; i < scale; i++) {
			sbStr.append("0");
		}
		DecimalFormat myformat = new DecimalFormat(sbStr.toString());
		return myformat.format(doubleVal);
	}

	/***
	 * Double类型相加 <font color="red">+</font><br/>
	 * ROUND_HALF_UP <font color="red">四舍五入</font><br/>
	 * 
	 * @param val1
	 * 
	 * @param val2
	 * 
	 * @param scale
	 *            <font color="red">保留scale位小数</font><br/>
	 * @return
	 */
	public static Double add(Double val1, Double val2, int scale) {
		if (null == val1) {
			val1 = new Double(0);
		}
		if (null == val2) {
			val2 = new Double(0);
		}
		return new BigDecimal(Double.toString(val1)).add(new BigDecimal(Double.toString(val2)))
				.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/***
	 * Double类型相减 <font color="red">—</font><br/>
	 * ROUND_HALF_UP <font color="red">四舍五入</font><br/>
	 * 
	 * @param val1
	 * 
	 * @param val2
	 * 
	 * @param scale
	 *            <font color="red">保留scale位小数</font><br/>
	 * @return
	 */
	public static Double subtract(Double val1, Double val2, int scale) {
		if (null == val1) {
			val1 = new Double(0);
		}
		if (null == val2) {
			val2 = new Double(0);
		}
		return new BigDecimal(Double.toString(val1)).subtract(new BigDecimal(Double.toString(val2)))
				.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/***
	 * Double类型相乘 <font color="red">*</font><br/>
	 * ROUND_HALF_UP <font color="red">四舍五入</font><br/>
	 * @param val1
	 * @param val2
	 * @param scale <font color="red">保留scale位小数</font><br/>
	 * @return
	 */
	public static Double multiply(Double val1, Double val2, int scale) {
		if (null == val1) {
			val1 = new Double(0);
		}
		if (null == val2) {
			val2 = new Double(0);
		}
		return new BigDecimal(Double.toString(val1)).multiply(new BigDecimal(Double.toString(val2)))
				.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/***
	 * Double类型相除 <font color="red">/</font><br/>
	 * ROUND_HALF_UP <font color="red">四舍五入</font><br/>
	 * @param val1
	 * @param val2
	 * @param scale  <font color="red">保留scale位小数</font><br/>
	 *
	 * @return
	 */
	public static Double divide(Double val1, Double val2, int scale) {
		if (null == val1) {
			val1 = new Double(0);
		}
		if (null == val2 || val2 == 0) {
			val2 = new Double(1);
		}
		return new BigDecimal(Double.toString(val1)).divide(new BigDecimal(Double.toString(val2)))
				.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/***
	 * Double类型取余 <font color="red">%</font><br/>
	 * ROUND_HALF_UP <font color="red">四舍五入</font><br/>
	 * @param val1
	 * @param val2
	 * @param scale <font color="red">保留scale位小数</font><br/>
	 * @return
	 */
	public static int divideAndRemainder(Double val1, Double val2, int scale) {
		if (null == val1) {
			val1 = new Double(0);
		}
		if (null == val2 || val2 == 0) {
			val2 = new Double(1);
		}
		return new BigDecimal(Double.toString(val1)).divideAndRemainder(new BigDecimal(Double.toString(val2)))[1]
				.setScale(scale, BigDecimal.ROUND_HALF_UP).intValue();
	}

	/***
	 * 格式化Double类型数据
	 * 
	 * @param val
	 * @param fmt
	 *            NumberFormat currency = NumberFormat.getCurrencyInstance();
	 *            //建立货币格式化引用 NumberFormat percent =
	 *            NumberFormat.getPercentInstance(); //建立百分比格式化引用
	 * @param maximumFractionDigits
	 *            如果是百分比 设置小数位数（四舍五入）
	 * @return
	 */
	public static String formatByNumberFormat(Double val, NumberFormat fmt, int maximumFractionDigits) {
		if (fmt.equals(NumberFormat.getPercentInstance())) {
			fmt.setMaximumFractionDigits(maximumFractionDigits); // 百分比小数点最多3位
		}
		return fmt.format(val);

	}

	/***
	 * 比较大小 -1、0、1，即左边比右边数大，返回1，相等返回0，比右边小返回-1。
	 * 
	 * @param
	 * @return
	 */
	public static int compareTo(Double val1, Double val2) {
		if (null == val1) {
			val1 = new Double(0);
		}
		if (null == val2) {
			val2 = new Double(0);
		}
		return new BigDecimal(val1).compareTo(new BigDecimal(val2));
	}

	/**
	 * 分转元（除以100）四舍五入，保留2位小数
	 * 
	 * @param amount
	 *            金额 元
	 * @return
	 */
	public static String changeF2Y(String amount) {
		return new BigDecimal(amount).divide(new BigDecimal(100)).setScale(2).toString();
	}

	/**
	 * 元转分（乘以100）
	 * 
	 * @param amount
	 *            金额 分（支持大数字，16位以上）
	 * @return
	 */
	public static String changeY2F(String amount) {
		return new BigDecimal(amount).multiply(new BigDecimal(100)).toString();
	}

	public static Long changeY2FLong(String amount) {
		return new BigDecimal(amount).multiply(new BigDecimal(100)).longValue();
	}
	
	/**
	 * 计算费率(采用4舍6入的方法计算,保留两位小数)
	 * 
	 * @param fee
	 *            浮点型数字
	 * @return
	 */
	public static String feeRounding(String fee) {
		BigDecimal bigDecimal = new BigDecimal(fee).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return bigDecimal.toString();
	}

	/**
	 * 计算费率(采用4舍6入的方法计算,保留两位小数),返回分
	 * 
	 * @param fee
	 *            浮点型数字
	 * @return
	 */
	public static Long feeRoundingF(BigDecimal initAmountF, BigDecimal fee) {
		BigDecimal bigDecimal = new BigDecimal("100");
//		initAmountF = initAmountF.divide(bigDecimal);// 转为元
		BigDecimal resultAmountY = initAmountF.multiply(fee); //费率计算
		BigDecimal resultAmountF = resultAmountY.setScale(2, BigDecimal.ROUND_HALF_EVEN).multiply(bigDecimal); //保留两位小数后转为分
		return resultAmountF.longValue();
	}

	public static Long changeY2FLongValue(String amount) {
		return new BigDecimal(amount).multiply(new BigDecimal(100)).longValue();
	}
	
	public static void main(String[] args) {
		BigDecimal amountBigDecimal = new BigDecimal("1.03"); 
		BigDecimal thirdChannelFee = new BigDecimal("0.038");
		Long amount = AmountUtil.feeRoundingF(amountBigDecimal, thirdChannelFee);
		System.out.println(amount);
	}
}