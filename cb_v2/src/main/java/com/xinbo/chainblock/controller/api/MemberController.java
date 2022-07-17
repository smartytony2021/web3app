package com.xinbo.chainblock.controller.api;

import com.xinbo.chainblock.consts.RedisConst;
import com.xinbo.chainblock.consts.StatusCode;
import com.xinbo.chainblock.entity.MemberEntity;
import com.xinbo.chainblock.service.MemberService;
import com.xinbo.chainblock.utils.JwtUser;
import com.xinbo.chainblock.utils.JwtUtil;
import com.xinbo.chainblock.utils.R;
import com.xinbo.chainblock.vo.MemberLoginVo;
import com.xinbo.chainblock.vo.RegisterVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Operation(summary = "register", description = "注册")
    @PostMapping("register")
    public R<Object> register(@RequestBody RegisterVo vo) {
        MemberEntity entity = MemberEntity.builder()
                .username(vo.getUsername())
                .pwd(vo.getPwd())
                .createTime(new Date())
                .version(1)
                .salt("1234")
                .money(0F)
                .build();

        boolean isSuccess = memberService.register(entity, vo.getCode());
        if (isSuccess) {
            redisTemplate.opsForValue().set(RedisConst.AGENT_FIXED, "1");
            return R.builder().data(StatusCode.SUCCESS).build();
        } else {
            return R.builder().data(StatusCode.REGISTER_ERROR).build();
        }
    }


    @Operation(summary = "login", description = "注册")
    @PostMapping("login")
    public R<Object> login(@RequestBody MemberLoginVo vo) {
        //生成token
        JwtUser jwtUser = JwtUser.builder()
                .uid(1)
                .username("admin")
                .build();
        String token = JwtUtil.generateToken(jwtUser);
        Map<String, String> map = new HashMap<>();
        map.put("token", String.format("Bearer %s", token));
        return R.builder().code(StatusCode.SUCCESS).data(map).build();
    }

}
