export const SurveyFormFields = {
  title: "title",
  description: "description",
  image: "image",
  tags: "tags",
  googleFormURL: "googleFormURL",
  questions: "questions",
};

export default class SurveyForm {
  constructor() {
    this.title = "";
    this.description = "";
    this.image = "";
    this.tags = [];
    this.googleFormURL = "";
    this.questions = [];
  }
}

export const SurveyFormValidation = {
  title: { required: true },
  description: { required: true },
  googleFormURL: { required: true },
  image: { required: true },
  tags: { required: true },
};
