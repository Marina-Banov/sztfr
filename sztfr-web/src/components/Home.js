import "../styles/Home.css";
import {signOut} from "../firebase";

export default function Home() {
    return (
        <div>
            <h3>HOME</h3>
            <button onClick={signOut}>Logout</button>
        </div>
    );
}
