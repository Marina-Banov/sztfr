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
  title: {
    required: {
      value: true,
      message: "Potrebno je unijeti naziv ankete",
    },
  },
  description: {
    required: {
      value: true,
      message: "Potrebno je unijeti opis ankete",
    },
  },
  image: {
    required: {
      value: true,
      message: "Potrebno je unijeti sliku ankete",
    },
  },
  tags: {
    required: {
      value: true,
      message: "Potrebno je odabrati tagove za anketu",
    },
  },
  googleFormURL: {
    required: {
      value: true,
      message: "Potrebno je unijeti link za ispunjavanje ankete",
    },
  },
};
