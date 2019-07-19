package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.AppUsers;
import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.util.AES;
import cn.mdsoftware.mdframework.common.util.SLEmojiFilter;
import cn.mdsoftware.mdframework.common.utils.RandomUtils;
import cn.mdsoftware.mdframework.service.MemberService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.riversoft.weixin.app.base.AppSetting;
import com.riversoft.weixin.app.user.SessionKey;
import com.riversoft.weixin.app.user.Users;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/wechat")
public class WechatController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 小程序登录
     * @param code 调用wx.login获取到的code
     * @param iv 调用wx.getUserInfo获取到
     * @param encryptedData 调用wx.getUserInfo获取到
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/appLogin", method = RequestMethod.POST)
    public HttpResponse<Object> appLogin(
            @NotBlank(message = "code不能为空") String code,
            @NotBlank(message = "iv不能为空")String iv,
            @NotBlank(message = "encryptedData不能为空")String encryptedData) throws Exception{
        HttpResponse<Object> resp = new HttpResponse<>();
        SessionKey sessionKey = Users.defaultUsers().code2Session(code);
        String sessionK = sessionKey.getSessionKey();
        byte[] resultByte  = AES.decrypt(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedData),
                org.apache.commons.codec.binary.Base64.decodeBase64(sessionK),  //sessionkey
                org.apache.commons.codec.binary.Base64.decodeBase64(iv));  //iv
       String  userInfo = new String(resultByte, "UTF-8");
        AppUsers appUsers = JSON.parseObject(userInfo, AppUsers.class);
        //查询会员
        Map<String,Object> map = new HashMap<>();
        map.put("unionId",appUsers.getUnionId());
//        map.put("openId",appUsers.getOpenId());
        PageInfo memberDOs= this.memberService.list(map);
        MemberDO memberDO = null;
        if (CollectionUtils.isNotEmpty(memberDOs.getList())) {
            memberDO = (MemberDO) memberDOs.getList().get(0);
            memberDO.setName(SLEmojiFilter.filterEmoji(appUsers.getNickName()));
            memberDO.setMainPic(appUsers.getAvatarUrl());
            this.memberService.update(memberDO);
        } else {
            memberDO = new MemberDO();
            memberDO.setName(SLEmojiFilter.filterEmoji(appUsers.getNickName()));
            memberDO.setMainPic(appUsers.getAvatarUrl());
            memberDO.setOpenId(appUsers.getOpenId());
            memberDO.setUnionId(appUsers.getUnionId());
            memberDO.setMobile("");
            this.memberService.save(memberDO);
        }
        //下发
        String token = RandomUtils.randomToken();
        redisTemplate.opsForValue().set(Constants.RedisKey.APP_SESSION.concat(token).concat(memberDO.getId().toString()), memberDO.getId(), 1, TimeUnit.DAYS);
        Map<String,Object> result = new HashMap<>();
        result.put("memberId",memberDO.getId());
        result.put("name",memberDO.getName());
        result.put("mainPic",memberDO.getMainPic());
        result.put("accessToken",token);
        resp.setData(result);
        return resp;
    }















    public static void main(String[] args) {
        AppSetting appSetting = new AppSetting();
        appSetting.setAppId("wxd0e2669443c1552e");
        appSetting.setSecret("39536761bee207cd598a2e73305f2651");
//
//        CodeMessage sessionKey = UserMessage.with(appSetting).code2Message("023YZVCW02MssU1qTqFW0YEcDW0YZVCv");


//        SessionKey aa = Users.with(appSetting).code2Session("033ww8NB1KfHHd0kJrNB1UspNB1ww8NG");

        String userInfo = null;
        JSONObject userInfoJSON = null;
        String a ="eiQdgdfv4+QsXewYllJ0nZ5sm6cP5tvTtN8maWOLoJ5DgBl/kve5P5KHTejci3J72rr7qLJMAP5BHef44RTprslE2uN3rz4R6BJBXPTw5l1jwdNi+riJzws7MJ0t1FGso0cBWzrk3XdUDQEW5TRcxxmV6LJlob7+vA1UdJV6pVgfl8TiyJql99WIKJ0Vlrq0jBrY7EQONwjU2rlSPJWmK/Gqs2q+7RS3T6ZlE9lOnnEu9y0w6MEoB0pmMYnHvlC0wcWvJI0JwijT6fWFlbei2BHaEVzw0JnkS2mnzdWnSiD60dtQwYkc4ubgp/eN9aZDjAbMdllpMddFfAxlZzXGyXnuhDFlxvhh+d9HkcLbXWgOhNjdR2eOvzexnuRIAV3NYw8NurzA/D+bVgBG5xvQvreSROsJ8PBPFBbtaKH7y3r8Wzs9IiXhxENrFyTggecoGqzGTzBMPP0GU6ODtSB0+vXB9ko8KV3GHOBx0BRIwWcAEfOZlrKSPIDnCTy8ggrPU6ws7QJx/OT2zfmEUOWfrRgVR1PLagst9vBco/gs8qI=";
        try {
            //Q3ZubGA3o2YQhawI59j+Fw==
            byte[] resultByte  = AES.decrypt(org.apache.commons.codec.binary.Base64.decodeBase64(a),
                    org.apache.commons.codec.binary.Base64.decodeBase64("tJCjhVXbDhLycfxeyM8wLQ=="),  //sessionkey
                    org.apache.commons.codec.binary.Base64.decodeBase64("nyayd4d+8pjgPZDhyTHMew=="));  //iv
            userInfo = new String(resultByte, "UTF-8");
            userInfoJSON = JSON.parseObject(userInfo);
            AppUsers appUsers = JSON.parseObject(userInfo, AppUsers.class);
            System.out.println(appUsers.toString());

           /* SessionKey sessionKey = Users.with(appSetting).code2Session("033R6uwy0nBZod1jOGwy03QAwy0R6uwD");
            String sessionK = sessionKey.getSessionKey();
            System.out.println(sessionK);*/


        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
