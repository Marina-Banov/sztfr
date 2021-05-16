import {
  CmsPage,
  Dashboard,
  Events,
  NotFound,
  Surveys,
} from "components/pages";
/*import Alerts from "vibe/Alerts";
import Loaders from "vibe/Loaders";
import Analytics from "vibe/Analytics";*/
import Widgets from "vibe/Widgets";
/*import Feed from "vibe/Feed";
import Modals from "vibe/Modals";*/

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
  /*{
    name: "Alerts",
    path: "/elements/alerts",
    component: Alerts,
  },
  {
    name: "Modals",
    path: "/elements/modals",
    component: Modals,
  },
  {
    name: "Loaders",
    path: "/elements/loaders",
    component: Loaders,
  },*/
  {
    name: "404",
    path: "/404",
    component: NotFound,
  },
  /*{
    name: "Analytics",
    path: "/apps/analytics",
    component: Analytics,
  },
  {
    name: "Activity Feed",
    path: "/apps/feed",
    component: Feed,
  },*/
  {
    name: "Widgets",
    path: "/widgets",
    component: Widgets,
  },
  {
    name: "Događaji",
    path: "/events",
    component: Events,
  },
  {
    name: "Novi događaj",
    path: "/events/new",
    component: CmsPage,
  },
  {
    name: "Ankete",
    path: "/surveys",
    component: Surveys,
  },
  {
    name: "Nova anketa",
    path: "/surveys/new",
    component: CmsPage,
  },
];

export default mainRoutes;
