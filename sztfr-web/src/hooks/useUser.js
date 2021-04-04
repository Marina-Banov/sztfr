import { useState, useEffect } from "react";
import firebase from "firebase";
import {authState} from "rxfire/auth";
import {auth} from "../firebase";

export default function useUser() {
  const [userState, setUserState] = useState<firebase.User>(null);
  const [loaded, setLoaded] = useState(false);

  useEffect(() => {
    const $user = authState(auth).subscribe(user => {
      setUserState(user);
      setLoaded(true);
    });
    return () => {
      $user.unsubscribe();
    };
  }, []);

  const isLoggedIn = () => {
    return loaded && userState !== null;
  };

  return { userState, loaded, isLoggedIn };
}
