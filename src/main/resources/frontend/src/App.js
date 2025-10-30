import React, { useEffect, useState } from "react";

function App() {
    const [message, setMessage] = useState("Loading...");

    useEffect(() => {
        fetch("http://localhost:8080/api/hello")
            .then((r) => r.text())
            .then((text) => setMessage(text))
            .catch((err) => setMessage("Error: " + err));
    }, []);

    return (
        <div style={{ padding: "2rem", fontFamily: "sans-serif" }}>
            <h1>React + Spring Boot Demo</h1>
            <p>Message from backend:</p>
            <h2>{message}</h2>
        </div>
    );
}

export default App;
