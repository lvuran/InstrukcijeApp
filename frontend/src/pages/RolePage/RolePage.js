import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChalkboardTeacher, faGraduationCap } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import { useNavigate } from "react-router-dom"; 
import Header from "../../components/Header/Header";
function ChooseRole(){

    const navigate = useNavigate();
    const [role, setRole] = useState("ROLE_STUDENT");

    const changeRole =  async ()  => {
        const roleDTO = { role: role };

        try {
            const response = await axios.patch(
                "http://localhost:8081/user/role/change",
                roleDTO,
                { withCredentials: true }
            );
            console.log(response);

            if(response.data.role === "ROLE_STUDENT"){
                navigate('/registration');
            } else if(response.data.role === "ROLE_TEACHER"){
                navigate('/tregistration');
            }
        } catch (e) {
            console.error("Greška u postavljanju uloge: ", e);
        }
    }

    return (
        <>
        <Header></Header><div className="container mt-5 text-center">
            <h4 className="mb-4">Ja sam:</h4>

            <div className="d-flex justify-content-center gap-3 mb-4">
                <button
                    className={`btn ${role === "ROLE_STUDENT" ? "btn-primary" : "btn-outline-primary"} d-flex align-items-center gap-2`}
                    onClick={() => setRole("ROLE_STUDENT")}
                >
                    <FontAwesomeIcon icon={faGraduationCap} /> Učenik
                </button>

                <button
                    className={`btn ${role === "ROLE_TEACHER" ? "btn-success" : "btn-outline-success"} d-flex align-items-center gap-2`}
                    onClick={() => setRole("ROLE_TEACHER")}
                >
                    <FontAwesomeIcon icon={faChalkboardTeacher} /> Instruktor
                </button>
            </div>

            <button className="btn btn-dark px-4" onClick={changeRole}>
                Potvrdi
            </button>
        </div></>
    );
}

export default ChooseRole;
