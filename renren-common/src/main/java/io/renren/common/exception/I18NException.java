/**
 * Copyright 2018 全球移动订阅 http://www.rockymobi.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.common.exception;

import org.springframework.lang.Nullable;

/**
 * 自定义国际化异常

* <p>Title: I18NException</p>  

* <p>Description: </p>  

* @author 8902

* @date 2018年11月19日
 */
public class I18NException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String code = "500";
	private String msg;

	public I18NException(String code, Throwable e) {
		super(e);
		this.code = code;
	}

	//	public I18NException(String code, String msg, Throwable e) {
	//		super(msg, e);
	//		this.msg = msg;
	//		this.code = code;
	//	}

	public I18NException(String code) {
		super(GeneratorMsg.getMessage(code));
		this.msg = GeneratorMsg.getMessage(code);
		this.code = code;
	}

	//	public I18NException(String code, String msg) {
	//		super(msg);
	//		this.msg = msg;
	//		this.code = code;
	//	}

	public I18NException(String code, @Nullable Object[] param) {
		super(GeneratorMsg.getMessage(code, param));
		this.msg = GeneratorMsg.getMessage(code, param);
		this.code = code;
	}

	public I18NException(String code, @Nullable Object[] param, Throwable e) {
		super(GeneratorMsg.getMessage(code, param), e);
		this.msg = GeneratorMsg.getMessage(code, param);
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
