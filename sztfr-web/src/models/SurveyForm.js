export const SurveyFormFields = {
  title: "title",
  description: "description",
  image: "image",
  tags: "tags",
  googleFormURL: "googleFormURL",
};

export default class SurveyForm {
  constructor() {
    this.title = "";
    this.description = "";
    this.image = "";
    this.tags = [];
    this.googleFormURL = "";
  }
}

export const SurveyFormValidation = {
  title: { required: true },
  description: { required: true },
  googleFormURL: { required: true },
  image: { required: true },
  tags: { required: true },
};
