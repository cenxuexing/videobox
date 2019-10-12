package io.renren.common.utils;


import io.renren.common.constant.CommonConstant;
import org.slf4j.Logger;

/**
 * 日志工具类
 *
 */
public class LoggerUtils {

    /**
     * debug输出级别
     *
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     */
    public static void debug(Logger logger, String message) {
        logger.debug("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message);
    }

    /**
     * info输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     * @param t  异常
     */
    public static void info(Logger logger, String message, Throwable t) {
        logger.info("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message, t);
    }

    /**
     * info输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     */
    public static void info(Logger logger, String message) {
        logger.info("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message);
    }

    /**
     * warn输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     * @param t 异常
     */
    public static void warn(Logger logger, String message, Throwable t) {
        logger.warn("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message, t);
    }

    /**
     * warn输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     */
    public static void warn(Logger logger, String message) {
        logger.warn("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message);
    }

    /**
     * error输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     * @param t 异常
     */
    public static void error(Logger logger, String message, Throwable t) {
        logger.error("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message, t);
    }

    /**
     * error输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     */
    public static void error(Logger logger, String message) {
        logger.error("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message);
    }

    /**
     * trace输出级别
     *
     * @param logger  日志对象
     * @param message 要打印的信息
     */
    public static void trace(Logger logger, String message) {
        logger.trace("[appName：" + CommonConstant.SYSTEM_NAME + "，times：" + System.currentTimeMillis() + "] - message：" + message);
    }
}
