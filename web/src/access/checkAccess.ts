import ACCESS_ENUM from "@/access/accessEnum";
/**
 * 判断当前登录用户是否具有某个权限
 * @param loginUser 当前登录用户
 * @param need 需要什么权限
 * return
 */

const checkAccess = (loginRole: any, need = ACCESS_ENUM.NOT_LOGIN) => {
  //获取当前用户具有什么权限
  if (need === ACCESS_ENUM.NOT_LOGIN) {
    return true;
  }
  if (need === ACCESS_ENUM.DEFAULT_USER) {
    if (loginRole === ACCESS_ENUM.NOT_LOGIN) {
      return false;
    }
  }
  if (need === ACCESS_ENUM.ADMIN) {
    if (loginRole !== ACCESS_ENUM.ADMIN) {
      return false;
    }
  }
  return true;
};

export default checkAccess;
