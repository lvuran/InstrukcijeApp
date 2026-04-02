import React, { useState } from "react";
import { useEffect } from "react";
import "./CreateLesson.css"
import axios from "axios";
import { useNavigate } from "react-router-dom"; 

import { useLocation } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "../../components/Header/Header";


function CreateLesson() {
  const location = useLocation();
  const { request } = location.state || {};
  const [teacher, setTeacher] = useState("")
const [error, setError] = useState(null);
const [selectedDate, setSelectedDate] = useState("");
const [selectedTime, setSelectedTime] = useState("");
    const navigate = useNavigate();

useEffect(() => {
      
      const fetchTeacher = async () =>{
      try{
        
      const res = await axios.get("http://localhost:8081/teacher/user", { withCredentials: true })
     setTeacher(res.data);
     console.log(res.data);
      }
      catch (error) {
                    console.error("Greška: ", error);
                    setError("Nije moguće dohvatiti instruktora");
      }
    
    }; fetchTeacher();}, [])
        
    const createLesson = async() => { 
      const dateTimeString = `${selectedDate}T${selectedTime}:00`; 

         const lessonDTO = {
            studentId: request.personId,
            dateTime: dateTimeString,
            subject: request.subject,
            type: teacher.type,
            description: request.description
        };

        try{
          console.log(lessonDTO);
            const response = await axios.post("http://localhost:8081/teacher/lesson", lessonDTO, {withCredentials: true});
            console.log(response);
        
                navigate('/teacherHome');
          
        } catch (e) {
            console.error("Greška u kreiranju lekcije: ", e);
        };

    }

  return (
    <><Header></Header><div className="container mt-4 mb-4">
      <h2 className="mb-4">Kreiraj lekciju</h2>

      {request && (
        <div className="card shadow-sm">
          <div className="card-body">
            <h5 className="card-title">Detalji zahtjeva</h5>
            <p className="card-text">
              <strong>Učenik:</strong> {request.person}
            </p>
            <p className="card-text">
              <strong>Predmet:</strong> {request.subject}
            </p>
               <p className="card-text">
              <strong>Razred:</strong> {request.grade}
            </p>
            <p className="card-text">
              <strong>Opis:</strong> {request.description}
            </p>
            <p className="card-text">
              <strong>Trajanje:</strong> {teacher.duration} minuta
            </p>
            <p className="card-text">
              <strong>Cijena:</strong> {teacher.price} €
            </p>
            <p className="card-text">
              <strong>Način održavanja:</strong> {teacher.type}
            </p>
            <p className="card-text">
              <strong>Adresa:</strong> {teacher.address}
            </p>
         

        
            <div className="border-top pt-3 mt-3">
              <h6 className="mb-3">Odaberi termin</h6>

              <div className="mb-3">
                <label htmlFor="lessonDate" className="form-label">Datum</label>
                <input
                  type="date"
                  id="lessonDate"
                  className="form-control w-25 h-25"
                  value={selectedDate}
                  onChange={(e) => setSelectedDate(e.target.value)} />
              </div>

              <div className="mb-3">
                <label htmlFor="lessonTime" className="form-label">Vrijeme</label>
                <input
                  type="time"
                  id="lessonTime"
                  className="form-control w-25 h-25"
                  value={selectedTime}
                  onChange={(e) => setSelectedTime(e.target.value)} />
              </div>

              <button
                className="btn btn-success w-100"
                onClick={() => createLesson()}
              >
                Spremi lekciju
              </button>
            </div>
          </div>
        </div>
      )}
    </div></>
  );
}
export default CreateLesson;