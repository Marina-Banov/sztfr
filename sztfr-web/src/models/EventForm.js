export const EventFormFields = {
  title: "title",
  description: "description",
  image: "image",
  tags: "tags",
  endDate: "endDate",
  endTime: "endTime",
  location: "location",
  locationIsOnline: "location.online",
  locationValueOnline: "location.valueOnline",
  locationValueOnsite: "location.valueOnsite",
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
    this.location = {
      online: null,
      valueOnline: "",
      valueOnsite: "",
    };
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
  "location.valueOnline": {
    isValid: (location) => {
      if (location.online === null) {
        return false;
      }
      if (location.online === false) {
        return true;
      }
      return location.valueOnline !== "";
    },
  },
  "location.valueOnsite": {
    isValid: (location) => {
      if (location.online === null) {
        return false;
      }
      if (location.online === true) {
        return true;
      }
      return location.valueOnsite.hasOwnProperty("place_id");
    },
  },
};
