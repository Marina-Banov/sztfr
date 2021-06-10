import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

export const deleteSubcollections =
  functions.firestore.document("surveys/{id}")
      .onDelete(async (snapshot, context) => {
        const q = await snapshot.ref.collection("questions").listDocuments();
        const r = await snapshot.ref.collection("results").listDocuments();

        const batch = admin.firestore().batch();

        q.map((val) => {
          batch.delete(val);
        });
        r.map((val) => {
          batch.delete(val);
        });

        return batch.commit();
      });
