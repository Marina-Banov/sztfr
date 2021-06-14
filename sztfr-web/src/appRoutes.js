import {
  Dashboard,
  Events,
  NewEvent,
  NewSurvey,
  NotFound,
  Surveys,
} from "components/pages";

const mainRoutes = [
  {
    name: "Početna",
    path: "/home",
    component: Dashboard,
  },
  {
    name: "Profil",
    path: "/profile",
    component: Dashboard,
  },
  {
    name: "404",
    path: "/404",
    component: NotFound,
  },
  {
    name: "Događaji",
    path: "/events",
    component: Events,
  },
  {
    name: "Novi događaj",
    path: "/events/new",
    component: NewEvent,
  },
  {
    name: "Ankete",
    path: "/surveys",
    component: Surveys,
  },
  {
    name: "Nova anketa",
    path: "/surveys/new",
    component: NewSurvey,
  },
];

export default mainRoutes;
