package io.renren.entity.bo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Valid
@Data
public class MerchantProductOperAtorBO {

	@NotBlank(message = "merchantCode not null")
	@ApiModelProperty(value = "商户号", example = "M20181106000002")
	private String merchantCode; // 商户code

	@NotBlank(message = "operatorCode not null")
	@ApiModelProperty(value = "运营商编号", example = "vodafone")
	private String operatorCode; // 运营商code

	@NotBlank(message = "productCode not null")
	@ApiModelProperty(value = "产品编码", example = "DWAP_GST0003_001_PAN05906011_A")
	private String productCode; // 产品code

	//	@NotBlank(message = "userMsisdn not null")
	@ApiModelProperty(value = "用户Msisdn编码", example = "919988901974")
	private String userMsisdn;

	//	@NotBlank(message = "userMsisdn not null")
	@ApiModelProperty(value = "用户imsi编码", example = "40411000000000")
	private String userImsi;
	
	@ApiModelProperty(value = "产品订单编码", example = "G201905160402030001")
	private String productOrderCode;

	@ApiModelProperty(value = "渠道来源", example = "360zs")
	private String channelCode;

	@ApiModelProperty(value = "渠道请求ID", example = "360zs")
	private String channelReqId;
	
	@ApiModelProperty(value = "短信验证码", example = "")
	private String pinCode;

	@ApiModelProperty(value = "请求ip", example = "")
	private String reqIp;
	
	@ApiModelProperty(value = "请求类型", example = "")
	private Integer reqType;

	@ApiModelProperty(value = "国家类别", example = "nepal")
	private String countryType;

	@ApiModelProperty(value = "运营商", example = "gp")
	private String operatorType;

	@ApiModelProperty(value = "订阅方式", example = "wap")
	private String subscribeType;

}
