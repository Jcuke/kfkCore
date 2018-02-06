package com.jp.subject.mmlbk.channel.pc.common;

import com.jp.base.excep.ApiException;
import com.jp.base.msg.RequestBase;
import com.jp.base.msg.ResponseBase;
import com.jp.base.target.MethodTarg;

public interface AdService {

	@MethodTarg(RESPClzz = EmptyResp.class, REQClzz = EmptyReq.class, describe = "广告新增")
	public abstract ResponseBase<EmptyResp> add(RequestBase<EmptyReq> req) throws ApiException;

}
