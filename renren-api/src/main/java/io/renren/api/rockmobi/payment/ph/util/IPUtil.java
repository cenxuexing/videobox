package io.renren.api.rockmobi.payment.ph.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

import io.renren.api.rockmobi.payment.ph.model.constant.BsnlSmppConstant;

public class IPUtil {

	public static List<Object> eastList = new ArrayList<Object>();
	public static List<Object> westList = new ArrayList<Object>();
	//	public static List<Object> sorthList = new ArrayList<Object>();
	public static List<Object> northList = new ArrayList<Object>();

	static {
		//添加东区 IP范围
		eastList.add(new SubnetUtils("117.227.240.0/21"));
		eastList.add(new SubnetUtils("117.249.64.0/18"));
		eastList.add(new SubnetUtils("117.249.96.0/19"));
		eastList.add(new SubnetUtils("117.251.128.0/18"));
		eastList.add(new SubnetUtils("117.237.192.0/18"));
		eastList.add(new SubnetUtils("117.252.224.0/19"));
		eastList.add(new SubnetUtils("103.56.176.0/22"));
		eastList.add(new SubnetUtils("117.232.0.0/18"));
		eastList.add(new SubnetUtils("117.238.0.0/17"));
		eastList.add(new SubnetUtils("117.226.128.0/17"));
		eastList.add(new SubnetUtils("117.233.0.0/17"));
		eastList.add(new SubnetUtils("117.227.0.0/17"));
		eastList.add(new SubnetUtils("117.227.128.0/18"));
		eastList.add(new SubnetUtils("117.227.192.0/19"));
		eastList.add(new SubnetUtils("117.227.224.0/20"));
		eastList.add(new SubnetUtils("45.116.124.0/22"));
		eastList.add(new SubnetUtils("45.116.28.0/22"));
		eastList.add(new SubnetUtils("59.98.16.0/20"));
		eastList.add(new SubnetUtils("59.98.64.0/19"));
		eastList.add(new SubnetUtils("103.56.64.0/22"));
		eastList.add(new SubnetUtils("59.98.208.0/20"));
		eastList.add(new SubnetUtils("59.98.8.0/21"));
		eastList.add(new SubnetUtils("10.80.0.0/12"));
		//eastList.add("202.149.25.201");

		//添加西区 IP范围
		westList.add(new SubnetUtils("117.228.0.0/17"));
		westList.add(new SubnetUtils("117.228.128.0/20"));
		westList.add(new SubnetUtils("117.229.8.0/21"));
		westList.add(new SubnetUtils("117.229.16.0/20"));
		westList.add(new SubnetUtils("117.229.32.0/19"));
		westList.add(new SubnetUtils("117.229.64.0/18"));
		westList.add(new SubnetUtils("117.233.0.0/18"));

		westList.add(new SubnetUtils("117.228.160.0/19"));
		westList.add(new SubnetUtils("117.228.192.0/19"));
		westList.add(new SubnetUtils("117.229.128.0/18"));
		westList.add(new SubnetUtils("117.232.192.0/18"));
		westList.add(new SubnetUtils("117.233.64.0/18"));

		//添加南区 IP范围
		//		sorthList.add("201.4.139.245");
		//		sorthList.add(new SubnetUtils("124.40.224.0/20"));

		//添加北区 IP范围
		northList.add(new SubnetUtils("117.226.50.0/23"));
		northList.add(new SubnetUtils("117.226.48.0/26"));
		northList.add(new SubnetUtils("117.224.0.0/16"));
		northList.add(new SubnetUtils("117.225.0.0/16"));
		northList.add(new SubnetUtils("117.226.52.0/22"));
		northList.add(new SubnetUtils("117.226.48.64/26"));
		northList.add(new SubnetUtils("117.226.49.0/24"));
		northList.add(new SubnetUtils("117.226.48.128/25"));
		northList.add(new SubnetUtils("117.234.0.0/16"));
	}

	/**
	 * 获取IP所属的运营商
	 * @param ip
	 * @return 运营商
	 */
	public static String judgeOp(String ip) {
		for (int i = 0; i < eastList.size(); i++) {
			Object object = eastList.get(i);
			if (object instanceof SubnetUtils) {
				SubnetUtils utils = (SubnetUtils) object;
				SubnetInfo info = utils.getInfo();
				if (info.isInRange(ip)) {
					return BsnlSmppConstant.BSNL_EAST;
				}
			} else {
				if (object.toString().equals(ip)) {
					return BsnlSmppConstant.BSNL_EAST;
				}
			}
		}

		for (int i = 0; i < westList.size(); i++) {
			Object object = westList.get(i);
			if (object instanceof SubnetUtils) {
				SubnetUtils utils = (SubnetUtils) object;
				SubnetInfo info = utils.getInfo();
				if (info.isInRange(ip)) {
					return BsnlSmppConstant.BSNL_WEST;
				}
			} else {
				if (object.toString().equals(ip)) {
					return BsnlSmppConstant.BSNL_WEST;
				}
			}
		}

		/*for (int i = 0; i < sorthList.size(); i++) {
			Object object = sorthList.get(i);
			if (object instanceof SubnetUtils) {
				SubnetUtils utils = (SubnetUtils)object;
				SubnetInfo info = utils.getInfo();
				if (info.isInRange(ip)) {
					return "bsnl-sorth";
				}
			}else {
				if(object.toString().equals(ip)){
					return "bsnl-sorth";
				}
			}
		}*/

		for (int i = 0; i < northList.size(); i++) {
			Object object = northList.get(i);
			if (object instanceof SubnetUtils) {
				SubnetUtils utils = (SubnetUtils) object;
				SubnetInfo info = utils.getInfo();
				if (info.isInRange(ip)) {
					return BsnlSmppConstant.BSNL_NORTH;
				}
			} else {
				if (object.toString().equals(ip)) {
					return BsnlSmppConstant.BSNL_NORTH;
				}
			}
		}

		return BsnlSmppConstant.BSNL_SOUTH;
	}

	//	public static void main(String[] args) {
	//		for (int i = 0; i < 10; i++) {
	//			Scanner scanner = new Scanner(System.in);
	//			System.out.println("请输入IP:------------");
	//			String ip = scanner.next();
	//			String operator = IPUtil.judgeOp(ip);
	//			System.out.println("属于BSNL的=========>" + operator);
	//			System.err.println("--------------------------------");
	//		}
	//	}
}
