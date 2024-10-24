import type { CancelablePromise } from "../core/CancelablePromise";
import { OpenAPI } from "../core/OpenAPI";
import { request as __request } from "../core/request";
import type { BaseResponseProblemSubmitVO } from "../models/BaseResponseProblemSubmitVO";
import type { ProblemSubmitAddDTO } from "../models/ProblemSubmitAddDTO";

export class ProblemSubmitControllerService {
  /**
   * @param requestBody
   * @returns BaseResponseProblemSubmitVO OK
   * @throws ApiError
   */
  public static doSubmit(
    requestBody: ProblemSubmitAddDTO
  ): CancelablePromise<BaseResponseProblemSubmitVO> {
    return __request(OpenAPI, {
      method: "POST",
      url: "/question_submit/doSubmit",
      body: requestBody,
      mediaType: "application/json",
    });
  }
}
