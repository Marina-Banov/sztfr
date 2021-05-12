import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

export const newUserSignUp = functions.auth.user().onCreate((user) => {
  if (user.providerData?.length) {
    return admin.firestore().collection("users").doc(user.uid).set({
      email: user.email,
      isAdmin: false,
    });
  }
});

export const userDeleted = functions.auth.user().onDelete((user) => {
  return admin.firestore().collection("users").doc(user.uid).delete();
});
