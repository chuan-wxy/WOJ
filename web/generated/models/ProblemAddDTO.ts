export type ProblemAddDTO = {
  problemId?: string;
  title?: string;
  author?: string;
  tagList?: Array<string>;
  timeLimit?: number;
  memoryLimit?: number;
  stackLimit?: number;
  description?: string;
  input?: string;
  output?: string;
  source?: string;
  difficulty?: number;
  auth?: number;
  judgeMode?: string;
  spjCode?: string;
  spjLanguage?: string;
};
