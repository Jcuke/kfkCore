package com.jp.base.dubbo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jp.base.bean.MethodBean;
import com.jp.base.dubbo.bean.DubboInArgsBean;
import com.jp.base.dubbo.bean.DubboOutArgsBean;
import com.jp.base.init.TXManager;
import com.jp.base.msg.ResponseBase;
import com.jp.base.tool.config.SYSConfig;
import com.jp.base.tool.utils.JsonUtil;
import com.jp.base.tool.utils.SpringUtils;

/**
 * 
 * TODO
 * 
 * @author: Administrator
 * @date: 2017年9月9日 下午4:03:05
 */
public class DubboServiceImpl implements DubboService {

	private static final Logger logger = LoggerFactory.getLogger(DubboServiceImpl.class);


	@Override
	public DubboOutArgsBean doPost(DubboInArgsBean inArgsBean) {
		System.out.println(" ===  DubboServiceImpl.doPost version:  "
				+ SYSConfig.coreVersion);
		// serviceID
		String txid = inArgsBean.getTxid();
		logger.info(txid + " begin: " + System.currentTimeMillis() / 1000);
		DubboOutArgsBean outArgsBean = new DubboOutArgsBean();
		outArgsBean.setTxid(txid);
		MethodBean methodBean = TXManager.getGlobal().getMethodBean(txid);
		if (null == methodBean) {
			// 返回失败对象
			outArgsBean.setResParam("未找到对应的service接口！");
		} else {
			try {
				Class<?> serviceObj = Class.forName(methodBean.getClassName());

				// 反序列化参数
				Object reqObj = JsonUtil.deserializeRequstVo(
						inArgsBean.getReqParam(),
						Class.forName(methodBean.getParamClassName()),
						Class.forName(methodBean.getReqActualType()));
				ResponseBase<?> resp = (ResponseBase<?>) invokeMethod(
						serviceObj, methodBean.getMethodName(), reqObj);
				outArgsBean.setResParam(JsonUtil.writeValueAsString(resp));
				if (resp != null && StringUtils.isNotEmpty(resp.getE())) {
					outArgsBean.setExceptionInfo(resp.getE());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info(txid + " end: " + System.currentTimeMillis() / 1000);
		return outArgsBean;
	}

	/**
	 * @Title: invokeMethod
	 * @Description: TODO
	 * @param serviceObj
	 * @param methodName
	 * @param reqObj
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	static <T> Object invokeMethod(Class<?> serviceObj, String methodName,
			Object reqObj)  {

		Method method = ReflectionUtils.findMethod(serviceObj, methodName,
				new Class[] { reqObj.getClass() });
		Object bean = SpringUtils.getBean(serviceObj);
		return ReflectionUtils.invokeMethod(method, bean, reqObj);
	}

}
