import { combineDateTime, getISOTime } from "utils/dateUtils";

export default class Event {
  constructor(form) {
    this.title = form.title;
    this.description = form.description;
    this.startTime = combineDateTime(
      form.startDate,
      getISOTime(form.startTime)
    );
    this.endTime = combineDateTime(form.endDate, getISOTime(form.endTime));
    this.location = form.location.online
      ? form.location.valueOnline
      : form.location.valueOnsite;
    this.image = form.image;
    this.organisation = form.organisation;
    this.tags = form.tags;
  }
}
