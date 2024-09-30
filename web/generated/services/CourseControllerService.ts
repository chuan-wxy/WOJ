/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseListCourse } from "../models/BaseResponseListCourse";
import type { BaseResponseString } from "../models/BaseResponseString";
import type { CourseAddDTO } from "../models/CourseAddDTO";
import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
export class CourseControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseString OK
   * @throws ApiError
   */
  public static addCourse(
    requestBody: CourseAddDTO
  ): CancelablePromise<BaseResponseString> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/course/add",
      body: requestBody,
      mediaType: "application/json",
    });
  }
  /**
   * @returns BaseResponseListCourse OK
   * @throws ApiError
   */
  public static getCourse(): CancelablePromise<BaseResponseListCourse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/course/get-course",
    });
  }
  /**
   * @param level
   * @returns BaseResponseListCourse OK
   * @throws ApiError
   */
  public static getCourseByLevel(
    level: number
  ): CancelablePromise<BaseResponseListCourse> {
    return __request(OpenAPI, {
      method: "GET",
      url: "/course/get-course-by-level",
      query: {
        level: level,
      },
    });
  }
}
