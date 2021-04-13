import "../styles/Home.css";
import {logOut} from "../firebase";

export default function Home() {
    return (
        <div>
            <h3>HOME</h3>
            <button onClick={logOut}>Logout</button>
        </div>
    );
}
