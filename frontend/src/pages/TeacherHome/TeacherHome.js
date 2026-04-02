import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom"; 
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "../../components/Header/Header";

function TeacherHome(){
const [requests, setRequests] = useState([]);
const [lessons, setLessons] = useState([]);
const [students, setStudents] = useState([]);    
const [error, setError] = useState(null);
const navigate = useNavigate();
const [info, setInfo] = useState({});
  const [activeTab, setActiveTab] = useState("lessons"); 


useEffect(() => {
      
      const fetchRequests = async () =>{
      try{
        
      const res = await axios.get("http://localhost:8081/teacher/allrequests", { withCredentials: true })
     setRequests(res.data);
     console.log(res.data);
      }
      catch (error) {
                    console.error("Greška: ", error);
                    setError("Nije moguće dohvatiti zahtjeve");
      }
    
    };


      const fetchLessons = async () =>{
      try{
        
      const res = await axios.get("http://localhost:8081/teacher/alllessons", { withCredentials: true })
     setLessons(res.data);
     console.log("lessons");
     console.log(res.data);
      }
      catch (error) {
                    console.error("Greška: ", error);
                    setError("Nije moguće dohvatiti lekcije");
      }
    
    };
    
      fetchRequests();
      fetchLessons();
        
    }, []);

    const handleDecision = async (id, decision, request) => {
    setRequests(prev =>
      prev.map(r =>
        r.id === id ? { ...r, active: false, decision } : r
      )
    );
    console.log(`Request ${id} was ${decision}`);
    console.log(request.personId);

     try{
            const response = await axios.patch(  `http://localhost:8081/teacher/request/${id}`,{}, {withCredentials: true});
            console.log(response);
           
        } catch (e) {
            console.error("Greška pri deaktiviranju zahtjeva ", e);
        };
           if(decision == "accepted")
    {
        navigate("/createLesson", { state: { request } });
    }
  };



  const sendInfo =  async (id, lessoninfo)  => {
     
        const infoDTO = {
            id:id,
            info: lessoninfo
        };
      
        try{
          console.log(infoDTO);
            const response = await axios.patch("http://localhost:8081/teacher/info", infoDTO, {withCredentials: true});
            console.log(response);
           
        } catch (e) {
            console.error("Greška u slanju informacija: ", e);
        };
         setLessons(prev => prev.filter(lesson => lesson.lessonId !== id));

    }



  const handleDecline =  async(id) =>{
      setLessons(prev => prev.filter(lesson => lesson.lessonId !== id));

    
     try{
            const response = await axios.patch(  `http://localhost:8081/teacher/lesson/${id}`,{}, {withCredentials: true});
            console.log(response);
           
              
            
        } catch (e) {
            console.error("Greška pri deaktiviranju zahtjeva ", e);
        };

  }



const handleTabChange = (tab) => setActiveTab(tab);




return (
  <>
    <Header />
    <div className="container mt-4 mb-4">
   
      <ul className="nav nav-tabs">
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "lessons" ? "active" : ""}`}
            onClick={() => handleTabChange("lessons")}
          >
            Nadolazeće lekcije
          </button>
        </li>
      
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "requests" ? "active" : ""}`}
            onClick={() => handleTabChange("requests")}
          >
            Aktivni zahtjevi
          </button>
        </li>
      </ul>

    
      <div className="tab-content mt-3">
        {activeTab === "lessons" && (
          <div>
            <h2 className="mb-3">Nadolazeće lekcije</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {lessons.sort((a, b) => new Date(a.dateTime) - new Date(b.dateTime)).map(lesson => (
                <div
                  key={lesson.lessonId}
                  className="p-4 border rounded-lg shadow bg-white"
                >
                  <h3 className="font-bold text-lg mb-2">
                    {lesson.subject} ({lesson.type})
                  </h3>

                  <p><strong>Student:</strong> {lesson.studentName}</p>
                  <p><strong>Datum:</strong> {new Date(lesson.dateTime).toLocaleString()}</p>
                  <p><strong>Razred:</strong>{lesson.grade}</p>
                  <p><strong>Trajanje:</strong> {lesson.duration}h</p>
                  <p><strong>Cijena:</strong> {lesson.price}€</p>
                  <p className="italic">{lesson.description}</p>

                  <input
                    type="text"
                    name="info"
                    id="info"
                    className="form-control form-control-sm my-2"
                    placeholder="Unesite povratnu informaciju"
                    value={info[lesson.lessonId] || ""}
                    onChange={(e) =>
                      setInfo(prev => ({
                        ...prev,
                        [lesson.lessonId]: e.target.value
                      }))
                    }
                  />

                  <div className="flex space-x-2 mt-2">
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() =>
                        sendInfo(lesson.lessonId, info[lesson.lessonId] || "")
                      }
                    >
                      Pošalji
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDecline(lesson.lessonId)}
                    >
                      Otkaži
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {activeTab === "requests" && (
          <div>
            <h2 className="mb-3">Aktivni zahtjevi</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {requests
                .filter(request => request.active)
                .map(r => (
                  <div
                    key={r.id}
                    className="p-4 border rounded-lg shadow bg-white"
                  >
                    <h3 className="font-bold text-lg mb-2">{r.subject}</h3>
                    <p><strong>Opis:</strong> {r.description}</p>
                    <p><strong>Učenik:</strong> {r.person}</p>
                    <p><strong>Razred:</strong> {r.grade}</p>

                    <div className="flex space-x-2 mt-3">
                      <button
                        onClick={() => handleDecision(r.id, "accepted", r)}
                        className="btn btn-success btn-sm"
                      >
                        Prihvati
                      </button>
                      <button
                        onClick={() => handleDecision(r.id, "declined", r)}
                        className="btn btn-danger btn-sm"
                      >
                        Odbij
                      </button>
                    </div>
                  </div>
                ))}
            </div>
          </div>
        )}
      </div>
    </div>
  </>
);

}
export default TeacherHome;