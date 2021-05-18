export const EventFormFields = {
  title: "title",
  description: "description",
  image: "image",
  tags: "tags",
  endDate: "endDate",
  endTime: "endTime",
  locationOnline: "locationOnline",
  locationOnsite: "locationOnsite",
  organisation: "organisation",
  startDate: "startDate",
  startTime: "startTime",
};

export default class EventForm {
  constructor() {
    this.title = "";
    this.description = "";
    this.image = "";
    this.tags = [];
    this.endDate = "";
    this.endTime = "";
    this.locationOnline = "";
    this.locationOnsite = "";
    this.organisation = "";
    this.startDate = "";
    this.startTime = "";
  }
}

export const EventFormValidation = {
  title: { required: true },
  description: { required: true },
  image: { required: true },
  tags: { required: true },
  organisation: { required: true },
  startDate: { required: true },
  startTime: { required: true },
  endDate: { required: true },
  endTime: { required: true },
};
