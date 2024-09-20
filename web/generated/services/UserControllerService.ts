import type { BaseResponseBoolean } from "../models/BaseResponseBoolean";
import type { BaseResponseListString } from "../models/BaseResponseListString";
import type { BaseResponseUserLoginVO } from "../models/BaseResponseUserLoginVO";
import type { BaseResponseVoid } from "../models/BaseResponseVoid";
import type { UserLoginDTO } from "../models/UserLoginDTO";
import type { UserLogoutDTO } from "../models/UserLogoutDTO";
import type { UserProfileDTO } from "../models/UserProfileDTO";
import type { UserRegisterDTO } from "../models/UserRegisterDTO";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class UserControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static updateProfile(
    requestBody: UserProfileDTO
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/update-profile",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static register(
    requestBody: UserRegisterDTO
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/register",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static logout(
    requestBody: UserLogoutDTO
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/logout",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static logoutForever(
    requestBody: UserLogoutDTO
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/logout-forever",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseUserLoginVO OK
   * @throws ApiError
   */
  public static login(
    requestBody: UserLoginDTO
  ): CancelablePromise<BaseResponseUserLoginVO> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/login",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @param userAccount
   * @returns BaseResponseListString OK
   * @throws ApiError
   */
  public static getRole(
    userAccount: string
  ): CancelablePromise<BaseResponseListString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/get-role",
      query: {
        userAccount: userAccount,
      },
    });
  }

  /**
   * @param requestBody
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static getLogoutCode(
    requestBody: UserLogoutDTO
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/get-logout-code",
      body: requestBody,
      mediaType: "application/json",
    });
  }

  /**
   * @returns BaseResponseUserLoginVO OK
   * @throws ApiError
   */
  public static getLoginUser(): CancelablePromise<BaseResponseUserLoginVO> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/user/get-loginuser",
    });
  }

  /**
   * @param email
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static getRegisterCode(
    email: string
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/get-register-code",
      query: {
        email: email,
      },
    });
  }

  /**
   * @param jwt
   * @returns BaseResponseBoolean OK
   * @throws ApiError
   */
  public static checkJwt(jwt: string): CancelablePromise<BaseResponseBoolean> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/user/check-jwt",
      query: {
        JWT: jwt,
      },
    });
  }
}
