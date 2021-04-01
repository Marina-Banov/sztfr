import { useEffect, useState } from "react";
import { verifyEmailLink, loginWithEmailLink, observer } from "../utils/firebase";

export default function LoginWithEmailLink() {
    const savedEmail = window.localStorage.getItem("emailForSignIn") || "";
    const [email, setEmail] = useState(savedEmail);
    const [errorResponse, setErrorResponse] = useState("");

    useEffect(() => {
        observer.subscribe('firebaseErrorEvent', data => {
            setErrorResponse(data);
        })
        verifyEmailLink(savedEmail);
        return () => { observer.unsubscribe('firebaseErrorEvent'); }
    }, [savedEmail]);

    const updateEmail = (e) => {
        setErrorResponse("");
        setEmail(e.target.value);
    };

    return (
        <div>
            <label>
                Email:
                <input type="text" value={email} onChange={updateEmail} />
            </label>
            <p>{errorResponse}</p>
            <button onClick={() => loginWithEmailLink(email)}>Sign in</button>
        </div>
    )
}
