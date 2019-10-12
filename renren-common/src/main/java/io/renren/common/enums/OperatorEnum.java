package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum OperatorEnum {
	NULL("", "", ""), //
	LIFECELL("lifecell", "lifecell", "lifecell"), //
	VODAFONE("vodafone", "vodafone", "vodafone"), //
	NTC("ntc", "ntc", "ntc"),//
	SMARTTEL("smarttel","smarttel","smarttel"),// 
	TELETALK("teletalk","teletalk","teletalk"),//
	ROBI("robi","robi","robi"),
	CELLCARD("cellcard","cellcard","cellcard");
	
	private String type;// 编号
	private String desc;// 备注
	private String operatorCode;//合作商编号

	private OperatorEnum(String type, String desc, String operatorCode) {
		this.type = type;
		this.desc = desc;
		this.operatorCode = operatorCode;
	}

	static Map<String, OperatorEnum> operatorEnumMap = new HashMap<>();
	static Map<String, OperatorEnum> productEnumMap = new HashMap<>();

	static {
		for (OperatorEnum operatorEnum : OperatorEnum.values()) {
			operatorEnumMap.put(operatorEnum.getType(), operatorEnum);
		}

		for (OperatorEnum operatorEnum : OperatorEnum.values()) {
			operatorEnumMap.put(operatorEnum.getOperatorCode(), operatorEnum);
		}
	}

	public static OperatorEnum convert(String type) {
		OperatorEnum operatorEnum = operatorEnumMap.get(type);

		if (operatorEnum == null) {
			return NULL;
		}

		return operatorEnum;
	}

	public static OperatorEnum convertByProdcutCode(String productCode) {
		OperatorEnum operatorEnum = operatorEnumMap.get(productCode);

		if (operatorEnum == null) {
			return NULL;
		}

		return operatorEnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public static Map<String, OperatorEnum> getOperatorEnumMap() {
		return operatorEnumMap;
	}

	public static void setOperatorEnumMap(Map<String, OperatorEnum> operatorEnumMap) {
		OperatorEnum.operatorEnumMap = operatorEnumMap;
	}

	public static Map<String, OperatorEnum> getProductEnumMap() {
		return productEnumMap;
	}

	public static void setProductEnumMap(Map<String, OperatorEnum> productEnumMap) {
		OperatorEnum.productEnumMap = productEnumMap;
	}

}
