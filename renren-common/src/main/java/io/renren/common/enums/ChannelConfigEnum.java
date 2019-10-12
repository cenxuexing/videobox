package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

/***
 * 渠道通知配置

* <p>Title: ChannelConfigEnum</p>  

* <p>Description: </p>  

* @author 8902

* @date 2019年1月18日
 */
public enum ChannelConfigEnum {

	//	渠道名：actwebmedia
	//	http://offers.actwebmedia.affise.com/postback?clickid={clickid}
	//	渠道名：pixelcarrier
	//	http://offers.pixelcarrier.affise.com/postback?clickid={clickid}
	//	渠道名：clickmob
	//	http://api.clickmobad.com/pb/ad/y08blcUFltubdZ6W1oq0veiAUCclNM67s1XccKwx?click_id={transaction_id}&payout=FIXED
	//	渠道名：adorca
	//	http://scs.adorca.com/sis?clickid={clickid}&payout={payout}
	//	渠道名：okyesmobi
	//	http://postback.okyesmobi.com?clickid={clickid}

	actwebmedia("actwebmedia", "http://offers.actwebmedia.affise.com/postback?clickid={click_id}", "{click_id}"), //
	pixelcarrier("pixelcarrier", "http://offers.pixelcarrier.affise.com/postback?clickid={click_id}", "{click_id}"), //
	clickmob("clickmob", "http://api.clickmobad.com/pb/ad/y08blcUFltubdZ6W1oq0veiAUCclNM67s1XccKwx?click_id={click_id}&payout=FIXED", "{click_id}"), //
	adorca("adorca", "http://scs.adorca.com/sis?clickid={clickid}", "{clickid}"), //&payout={payout}
	okyesmobi("okyesmobi", "http://postback.okyesmobi.com?clickid={clickid}&offerid=159", "{clickid}"), //
	adluminious("adluminious", "http://offers.adluminious.affise.com/postback?clickid={YOURCLICKID}", "{YOURCLICKID}"), //&sub2={YOURPUBID}

	leadmob("leadmob", "http://postback.leadmob-ad.com/emmet/pb/ad1220?orderid={click_id}", "{click_id}"), //

	MOBITIZE("MOBITIZE", "http://159.89.232.10/handler/controller/conversion?tid={click_id}", "{click_id}"), //
	
	smarter("smarter", "http://cpa.smarter-wireless.net/cpa/index.php?m=advert&p=postback&source=panshihz&clickid={click_id}", "{click_id}"), //
	
	bitterstrawberry("bitterstrawberry", "https://callbacks.bitterstrawberry.org/?token=de56b98d36bd595baca972ea9b2cd205&hash={click_id}&transaction_id=unique.id&amount=x.xx&payout_type=pps&currency=USD", "{click_id}"), //
	
	mobpals("mobpals", "http://mobielite.fuse-cloud.com/pb?tid={click_id}", "{click_id}"), //
	
	mojomobi("mojomobi", "http://pix.mojomobi.com/ads/th.php?cid={click_id}", "{click_id}"), //

	wemobi("wemobi", "http://p.moceanwp.com:9093/wap/spdata?cid={click_id}", "{click_id}");//

	private String channelCode;// 渠道编号
	private String channelNotifyUrl;// 通知地址
	private String channelNotifyParam;//合作商编号

	private ChannelConfigEnum(String channelCode, String channelNotifyUrl, String channelNotifyParam) {
		this.channelCode = channelCode;
		this.channelNotifyUrl = channelNotifyUrl;
		this.channelNotifyParam = channelNotifyParam;
	}

	static Map<String, ChannelConfigEnum> channelMap = new HashMap<>();

	static {
		for (ChannelConfigEnum channelConfigEnum : ChannelConfigEnum.values()) {
			channelMap.put(channelConfigEnum.getChannelCode(), channelConfigEnum);
		}
	}

	public static ChannelConfigEnum convert(String channelCode) {
		ChannelConfigEnum operatorEnum = channelMap.get(channelCode);

		if (operatorEnum == null) {
			return null;
		}

		return operatorEnum;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelNotifyUrl() {
		return channelNotifyUrl;
	}

	public void setChannelNotifyUrl(String channelNotifyUrl) {
		this.channelNotifyUrl = channelNotifyUrl;
	}

	public String getChannelNotifyParam() {
		return channelNotifyParam;
	}

	public void setChannelNotifyParam(String channelNotifyParam) {
		this.channelNotifyParam = channelNotifyParam;
	}

	
	public static void main(String[] args) {
		for (ChannelConfigEnum channelConfigEnum : ChannelConfigEnum.values()) {
			String sql = "INSERT INTO `renren_security`.`sys_dict`(`name`, `type`, `code`, `value`, `order_num`, `remark`, `del_flag`) VALUES ('推广渠道URL', 'notifySubChannel', '"+channelConfigEnum.getChannelCode()+"', '"+channelConfigEnum.getChannelNotifyUrl()+"', 0, NULL, 0);";
			System.out.println(sql);
		}
	}
}
