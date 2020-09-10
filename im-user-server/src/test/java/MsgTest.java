
import cn.hutool.core.util.RandomUtil;
import com.app.core.constant.BangHuCon;
import com.app.core.external.entity.banghu.*;
import com.app.core.external.entity.banghu.common.BhtRootRes;
import com.app.core.external.service.CallBhtService;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: rcs
 * @Date: 2020/07/22/7:58
 * @Description:
 */
public class MsgTest {
    private XmlMapper xmlMapper = new XmlMapper();

    @Before
    public void init() {
    }


    @SneakyThrows
    @Test
    public void bhtCallByTransCodeAndBodyTest() {//注册用户检查
        CallBhtService callBhtService = new CallBhtService();
        BhtUserRegisterIdCheckReq registerIdCheckReq = new BhtUserRegisterIdCheckReq();
        registerIdCheckReq.setUserLoginId("123456789");
        BhtRootRes<BhtUserRegisterIdCheckRes> resDto = callBhtService
                .callByTransCodeAndBody(BangHuCon.USER_REGISTER_CHECK, registerIdCheckReq, BhtUserRegisterIdCheckRes.class);
        System.out.println(resDto);
    }

    @SneakyThrows
    @Test
    public void bhtCallSendMsgTest() {//发送验证码接口
        CallBhtService callBhtService = new CallBhtService();
        BhtVerifyCodeSendReq req = new BhtVerifyCodeSendReq();
        req.setMobile("18610758765");
        req.setUserLoginId("18610758765");
        BhtRootRes<BhtVerifyCodeSendRes> res = callBhtService.callByTransCodeAndBody(BangHuCon.SMS_VERIFY_CODE_GET, req, BhtVerifyCodeSendRes.class);
        System.out.println(res.getSysHead());
        System.out.println(res);
    }

    @SneakyThrows
    @Test
    public void bhtCallCheckMsgTest() {//二维码验证接口
        CallBhtService bh = new CallBhtService();
        BhtVerifyCodeCheckReq req = new BhtVerifyCodeCheckReq();
        req.setMobile("18610758765");
        req.setUserLoginId("18610758765");
        req.setVeririfyCode("666666");//固定 666666
        BhtRootRes<BhtVerifyCodeCheckRes> res = bh.callByTransCodeAndBody(BangHuCon.SMS_VERIFY_CODE_CHECK, req, BhtVerifyCodeCheckRes.class);
        System.out.println(res.getSysHead().getRspMsg() + "-返回码-" + res.getSysHead().getRspCode());
    }

    @SneakyThrows
    @Test
    public void bhtcheuserTest() {//用户注册检查
        CallBhtService bh = new CallBhtService();
        BhtUserRegisterIdCheckReq req = new BhtUserRegisterIdCheckReq();
        req.setUserLoginId("186107587651");
        BhtRootRes res = bh.callByTransCodeAndBody(BangHuCon.USER_REGISTER_CHECK, req, BhtUserRegisterIdCheckRes.class);
        System.out.println(res);

    }

    @SneakyThrows
    @Test
    public void enerateTokenReq() {//生成令牌接口
        CallBhtService bh = new CallBhtService();
        BhtGenerateTokenReq req = new BhtGenerateTokenReq();
        req.setUserLoginId("18610758765");
        BhtRootRes<BhtGenerateTokenRes> res = bh.callByTransCodeAndBody(BangHuCon.GENERATE_TOKEN, req, BhtGenerateTokenRes.class);
        System.out.println(res);
    }

    @SneakyThrows
    @Test
    public void accessToken() {//校验令牌接口
        CallBhtService bh = new CallBhtService();
        BhtAccessTokenReq req = new BhtAccessTokenReq();
        req.setAccessToken("");
        BhtRootRes res = bh.callByTransCodeAndBody(BangHuCon.ACCESS_TOKEN, req, BhtAccessTokenReq.class);
        System.out.println(res);
    }

    @SneakyThrows
    @Test
    public void userPasswdLogin() {//用户名密码登录
        CallBhtService bh = new CallBhtService();
        BhtUserLoginReq req = new BhtUserLoginReq();
        req.setLoginPwd("123456");
        req.setUserLoginId("18610758765");
        BhtRootRes<BhtUserLoginRes> res = bh.callByTransCodeAndBody(BangHuCon.USER_LOGIN, req, BhtUserLoginRes.class);
        System.out.println(res);
    }

    @SneakyThrows
    @Test
    public void userRegister() {//用户注册
        CallBhtService bh = new CallBhtService();
        BhtUserRegisterReq req = new BhtUserRegisterReq();
        req.setMobile("1861075876511");
        req.setLoginPwd("1231231");
        req.setUserLoginName("1861075876511");
        BhtRootRes<BhtUserRegisterRes> res = CallBhtService.callByTransCodeAndBody(BangHuCon.USER_REGISTER, req, BhtUserRegisterRes.class);
        System.out.println(res);
        System.out.println("用户id-------"+res.getTransBody().getUserId());
    }

    @Test
    public void sdsd() {
        String s = RandomUtil.randomString(10);
        System.out.println(s);
    }

    @SneakyThrows
    @Test
    public void UpdatePassword() {//修改密码
        BhtUserPwdChangeReq req = new BhtUserPwdChangeReq();
        req.setNewLoginPwd("123");
        req.setLoginPwd("1231");
        req.setUserLoginId("123");
        BhtRootRes<BhtUserPwdChangeRes> res = CallBhtService.callByTransCodeAndBody(BangHuCon.USER_PASSWORD_CHANGE, req, BhtUserPwdChangeRes.class);
        System.out.println(res);
    }

    @SneakyThrows
    @Test
    public void updateMobie() {//修改手机号
        BhtUserMobileChangeReq req = new BhtUserMobileChangeReq();
        req.setMobile("13812344321");
        req.setUserLoginId("18610758765");
        req.setVeririfyCode("123123");
        BhtRootRes<BhtUserMobileChangeRes> res = CallBhtService.callByTransCodeAndBody(BangHuCon.USER_PHONE_CHANGE, req, BhtUserMobileChangeRes.class);
        System.out.println(res);
    }

    @SneakyThrows
    @Test
    public void forgetPwd() {// 密码重置
        BhtUserPwdResetReq req = new BhtUserPwdResetReq();
        req.setNewLoginPwd("1234");
        req.setVeririfyCode("12211");
        req.setUserLoginId("18510758765");
        req.setMobile("18510758765");
        BhtRootRes<BhtUserPwdResetRes> res = CallBhtService.callByTransCodeAndBody(BangHuCon.USER_PASSWORD_RESET, req, BhtUserPwdResetRes.class);
        System.out.println(res);
    }

    //userId=fef6550a8bbe4d16a2e536a703e21234
    @SneakyThrows
    @Test
    public void getUserInfoByUserLoginId() {// 密码重置
        BhtUserInfoQueryReq req = new BhtUserInfoQueryReq();
        req.setUserLoginId("186910758765");
        BhtRootRes<BhtUserInfoQueryRes> res = CallBhtService.callByTransCodeAndBody(BangHuCon.USER_INFO_QUERY, req, BhtUserInfoQueryRes.class);
        System.out.println(res);
    }
}
