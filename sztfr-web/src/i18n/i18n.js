import i18n from "i18next";
import { initReactI18next } from "react-i18next";

import translationHR from "./locales/hr/translation.json";

i18n
  .use(initReactI18next)
  .init({
    resources: {
      hr: { translation: translationHR },
    },
    lng: "hr",
    interpolation: {
      escapeValue: false,
    },
  })
  .then();

export default i18n;
