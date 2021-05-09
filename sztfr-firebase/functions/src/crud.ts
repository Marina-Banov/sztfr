import * as admin from "firebase-admin";
import express = require("express");
import cors = require("cors");
import { Express } from "express";

export const crudOperations = (collectionPath: string): Express => {
  const app = express();
  app.use(cors());

  app.get("/", (req, res) => {
    admin.firestore().collection(collectionPath).get()
        .then((querySnapshot) => {
          console.log("GET success");
          const arr: unknown[] = [];
          querySnapshot.forEach((doc) => {
            arr.push({ id: doc.id, ...doc.data() });
          });
          res.send(arr);
        })
        .catch((error) => {
          console.error("GET error", error);
          res.send(error);
        });
  });

  app.get("/:id", (req, res) => {
    admin.firestore().collection(collectionPath).doc(req.params.id).get()
        .then((doc) => {
          if (doc.exists) {
            console.log("GET success", req.params.id);
            res.send(doc.data());
          } else {
            console.log("GET undefined", req.params.id);
            res.send({ message: "Not found", status: 404 });
          }
        }).catch((error) => {
          console.error("GET error", error);
          res.send(error);
        });
  });

  app.post("/", (req, res) => {
    admin.firestore().collection(collectionPath).add(req.body)
        .then((doc) => {
          console.log("POST success", doc.id);
          res.send(doc);
        })
        .catch((error) => {
          console.error("POST error", error);
          res.send(error);
        });
  });

  app.put("/:id", (req, res) => {
    admin.firestore().collection(collectionPath).doc(req.params.id)
        .set(req.body)
        .then((doc) => {
          console.log("PUT success", req.params.id);
          res.json({ message: "Successfully updated", status: 200 });
        })
        .catch((error) => {
          console.error("PUT error", error);
          res.send(error);
        });
  });

  app.delete("/:id", (req, res) => {
    admin.firestore().collection(collectionPath).doc(req.params.id).delete()
        .then((doc) => {
          console.log("DELETE success", req.params.id);
          res.json({ message: "Successfully deleted", status: 200 });
        })
        .catch((error) => {
          console.error("DELETE error", error);
          res.send(error);
        });
  });

  return app;
};
