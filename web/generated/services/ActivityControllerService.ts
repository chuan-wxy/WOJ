import type { ActivityAddDTO } from "../models/ActivityAddDTO";
import type { BaseResponseActivityContentVO } from "../models/BaseResponseActivityContentVO";
import type { BaseResponseListActivityTitleVO } from "../models/BaseResponseListActivityTitleVO";
import type { BaseResponseString } from "../models/BaseResponseString";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class ActivityControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseString OK
   * @throws ApiError
   */
  public static addActivity(
    requestBody: ActivityAddDTO
  ): CancelablePromise<BaseResponseString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/activity/add",
      body: requestBody,
      mediaType: "application/json",
    });
  }
  /**
   * @param id
   * @returns BaseResponseActivityContentVO OK
   * @throws ApiError
   */
  public static getActivity(
    id: number
  ): CancelablePromise<BaseResponseActivityContentVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/activity/get-activity",
      query: {
        id: id,
      },
    });
  }
  /**
   * @returns BaseResponseListActivityTitleVO OK
   * @throws ApiError
   */
  public static getActivityList(): CancelablePromise<BaseResponseListActivityTitleVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/activity/get-activity-list",
    });
  }
}
