import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

import "./Header.css";
import { useNavigate } from "react-router-dom";

import axios from "axios";
function Header() {
    const navigate = useNavigate();
const user = JSON.parse(sessionStorage.getItem("user"));

const [role, setRole] = useState("");
    const handleLogout = async() => {

        try {
    await axios.post("http://localhost:8081/user/logout", {}, { withCredentials: true });
  } catch (e) {
    console.error("Logout failed", e);
  }

        console.log(user)
         localStorage.removeItem("access_token");
        sessionStorage.removeItem("user");

        navigate("/");
    };


  useEffect(() => {
    const fetchrole = async () => {
      try {
        const res = await axios.get("http://localhost:8081/user/current-role", { withCredentials: true });
        setRole(res.data);
      } catch (error) {
        console.error("Korisnik nije prijavljen");
       
      }
    };
    fetchrole();
  }, []);

    
    

    return (
  <header className="header">
    
      <div className="header-left">
        
        {role === "ROLE_STUDENT" && (
          <><Link to="/home" className="header-link">
              Početna
            </Link><Link to="/search" className="header-link">
                Pretraga
              </Link></> )}
               {role === "ROLE_TEACHER" && (
          <><Link to="/teacherhome" className="header-link">
              Početna
            </Link>
      </> )}
      </div>
      <div className="header-right">
        {((role === "ROLE_TEACHER") || (role=== "ROLE_STUDENT")) && (
        <button onClick={handleLogout} className="logout-btn">
          Odjava
        </button>)}
      </div>

          
      
    </header>
  );
}

export default Header;
