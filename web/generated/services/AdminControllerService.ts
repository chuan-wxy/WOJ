import type { BaseResponseListUserVO } from "../models/BaseResponseListUserVO";
import type { BaseResponseUserVO } from "../models/BaseResponseUserVO";
import type { BaseResponseVoid } from "../models/BaseResponseVoid";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class AdminControllerService {
  /**
   * @param gender
   * @returns BaseResponseListUserVO OK
   * @throws ApiError
   */
  public static searchUserByGender(
    gender: string
  ): CancelablePromise<BaseResponseListUserVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/amdin/search-list-by-gender",
      query: {
        gender: gender,
      },
    });
  }
  /**
   * @param uuid
   * @returns BaseResponseUserVO OK
   * @throws ApiError
   */
  public static searchUserById(
    uuid: string
  ): CancelablePromise<BaseResponseUserVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/amdin/search-by-uuid",
      query: {
        uuid: uuid,
      },
    });
  }
  /**
   * @param uuid
   * @returns BaseResponseVoid OK
   * @throws ApiError
   */
  public static deleteUserByUuid(
    uuid: string
  ): CancelablePromise<BaseResponseVoid> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/amdin/delete-by-uuid",
      query: {
        uuid: uuid,
      },
    });
  }
}
