import type { AnnouncementAddDTO } from "../models/AnnouncementAddDTO";
import type { BaseResponseAnnouncementContentVO } from "../models/BaseResponseAnnouncementContentVO";
import type { BaseResponseListAnnouncementTitleVO } from "../models/BaseResponseListAnnouncementTitleVO";
import type { BaseResponseString } from "../models/BaseResponseString";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";

export class AnnouncementControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseString OK
   * @throws ApiError
   */
  public static addAnnouncement(
    requestBody: AnnouncementAddDTO
  ): CancelablePromise<BaseResponseString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/announcement/add",
      body: requestBody,
      mediaType: "application/json",
    });
  }
  /**
   * @param id
   * @returns BaseResponseAnnouncementContentVO OK
   * @throws ApiError
   */
  public static getAnnouncement(
    id: number
  ): CancelablePromise<BaseResponseAnnouncementContentVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/announcement/get-activity",
      query: {
        id: id,
      },
    });
  }
  /**
   * @returns BaseResponseListAnnouncementTitleVO OK
   * @throws ApiError
   */
  public static getAnnouncementList(): CancelablePromise<BaseResponseListAnnouncementTitleVO> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/announcement/get-activity-list",
    });
  }
}
