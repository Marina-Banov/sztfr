import constants from "appConstants";

export default class Survey {
  constructor(form) {
    this.title = form.title;
    this.description = form.description;
    this.image = constants.STORAGE_SURVEYS_PATH + form.image.name;
    this.tags = form.tags;
    this.published = false;
  }
}

export class Questions {
  constructor(form) {
    const qs = [...form.questions];
    qs.forEach((q, i) => (q.order = i + 1));
    this.questions = qs;
  }
}
