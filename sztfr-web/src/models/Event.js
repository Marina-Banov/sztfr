import { combineDateTime } from "utils/dateUtils";

export default class Event {
  constructor(form) {
    this.title = form.title;
    this.description = form.description;
    this.startTime = combineDateTime(form.startDate, form.startTime);
    this.endTime = combineDateTime(form.endDate, form.endTime);
    this.location = form.locationOnline
      ? form.locationOnline
      : form.locationOnsite;
    this.image = form.image;
    this.organisation = form.organisation;
    this.tags = form.tags;
  }
}
