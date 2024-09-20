package org.chuan.woj.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.vo.user.UserVO;

import java.util.List;

/**
 * 特殊接口
 *
 * @Author chuan-wxy
 * @Create 2024/8/18 15:34
 */
public interface AdminService extends IService<User> {
    BaseResponse<UserVO> searchUserById(String uuid);

    BaseResponse<List<UserVO>> searchUserByGneder(String gender);

    BaseResponse<Void> deleteUserByUuid(String uuid);
}
