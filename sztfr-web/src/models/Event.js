import constants from "appConstants";
import { combineDateTime, getISOTime } from "utils/dateUtils";

export default class Event {
  constructor(form) {
    this.title = form.title;
    this.description = form.description;
    this.startTime = new Date(
      combineDateTime(form.startDate, getISOTime(form.startTime))
    );
    this.endTime = new Date(
      combineDateTime(form.endDate, getISOTime(form.endTime))
    );
    this.online = form.location.online;
    this.location = form.location.online
      ? form.location.valueOnline
      : form.location.valueOnsite.place_id;
    this.image = constants.STORAGE_EVENTS_PATH + form.image.name;
    this.organisation = form.organisation;
    this.tags = form.tags;
    this.subscribers = [];
  }
}
