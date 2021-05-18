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
  title: {
    required: {
      value: true,
      message: "Potrebno je unijeti naziv događaja",
    },
  },
  description: {
    required: {
      value: true,
      message: "Potrebno je unijeti opis događaja",
    },
  },
  image: {
    required: {
      value: true,
      message: "Potrebno je unijeti sliku događaja",
    },
  },
  tags: {
    required: {
      value: true,
      message: "Potrebno je odabrati tagove za događaj",
    },
  },
};
