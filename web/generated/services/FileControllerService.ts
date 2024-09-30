/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseString } from "../models/BaseResponseString";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
export class FileControllerService {
  /**
   * @param file
   * @param pid
   * @returns BaseResponseString OK
   * @throws ApiError
   */
  public static uploadJudgeList(
    file: Blob,
    pid: string
  ): CancelablePromise<BaseResponseString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/file/upload-judgelist",
      query: {
        file: file,
        pid: pid,
      },
    });
  }
  /**
   * @param file
   * @param courseName
   * @returns BaseResponseString OK
   * @throws ApiError
   */
  public static uploadCourseAvatar(
    file: Blob,
    courseName: string
  ): CancelablePromise<BaseResponseString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/file/upload-course-avatar",
      query: {
        file: file,
        courseName: courseName,
      },
    });
  }
  /**
   * @returns string OK
   * @throws ApiError
   */
  public static uploadAvatar(): CancelablePromise<string> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/file/upload-avatar",
    });
  }
}
