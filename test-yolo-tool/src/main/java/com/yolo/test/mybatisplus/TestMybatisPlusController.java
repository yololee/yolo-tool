package com.yolo.test.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yolo.test.mybatisplus.model.User;
import com.yolo.common.api.R;
import com.yolo.common.support.Kv;
import com.yolo.mybatisplus.support.Condition;
import com.yolo.mybatisplus.support.PageQuery;
import com.yolo.mybatisplus.support.SqlKeyword;
import com.yolo.mybatisplus.support.TableDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mybatis/plus")
@RequiredArgsConstructor
public class TestMybatisPlusController {

    private final UserMapper userMapper;

    /**
     * 分页查询
     * @return {@link R}<{@link TableDataInfo}<{@link User}>>
     */
    @GetMapping("/page")
    public R<TableDataInfo<User>> page(){
        QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(Kv.init().set("name" + SqlKeyword.EQUAL,111), User.class);
        IPage<User> page = Condition.getPage(new PageQuery().setCurrent(1).setSize(10));
        IPage<User> userPage = userMapper.selectVoPage(page, queryWrapper);
        TableDataInfo<User> build = TableDataInfo.build(userPage);
        return R.data(build);
    }

    @GetMapping("/one")
    public R<User> one(){
        User user = new User();
        user.setAge(21);
        QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user);
        User user1 = userMapper.selectVoOne(queryWrapper);
        return R.data(user1);
    }

}
