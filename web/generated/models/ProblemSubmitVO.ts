/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { JudgeInfo } from './JudgeInfo';
import type { UserVO } from './UserVO';
export type ProblemSubmitVO = {
    id?: number;
    language?: string;
    code?: string;
    judgeInfo?: JudgeInfo;
    state?: number;
    pid?: number;
    uid?: string;
    userVO?: UserVO;
};

