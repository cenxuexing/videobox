package io.renren.api.cache;

public class CacheConstant {

	public static final String KEY_ROOT_CLASS_METHOD = "#root.targetClass + #root.methodName";

	public static final String KEY_ROOT_CLASS_METHOD_PARAMARRAY = "#root.targetClass + #root.methodName + #root.args";

	public static final String NAME_COMMON_CACHE_300s = "commonCache_300s";

	public static final String NAME_COMMON_CACHE_60s = "commonCache_60s";

	public static final String NAME_COMMON_CACHE_5s = "commonCache_5s";

	public static final String KEY_GENERATOR = "cacheKeyGenerator";
}
