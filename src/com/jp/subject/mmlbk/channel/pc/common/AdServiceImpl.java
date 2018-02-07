package com.jp.subject.mmlbk.channel.pc.common;

import org.springframework.stereotype.Service;

import com.jp.base.excep.ApiException;
import com.jp.base.msg.RequestBase;
import com.jp.base.msg.ResponseBase;

@Service
public class AdServiceImpl implements AdService {

    @Override
    public ResponseBase<EmptyResp> add(RequestBase<EmptyReq> req) throws ApiException {
        EmptyReq body = req.getBody();
        System.out.println("8011的 core 运行了 corePort=3100 ");
        try {
            body.check();
        } catch (ApiException e) {
        }
        return new ResponseBase();
    }


}
