import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
import * as user from "./user";
import { crudOperations } from "./crud";

admin.initializeApp();

export const newUserSignUp = user.newUserSignUp;
export const userDeleted = user.userDeleted;
export const events = functions.https.onRequest(crudOperations("events"));
export const surveys = functions.https.onRequest(crudOperations("surveys"));
