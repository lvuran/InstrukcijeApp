import { useState } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom"; 
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "../../components/Header/Header";

function StudentHome(){
const [requests, setRequests] = useState([]);
const [lessons, setLessons] = useState([]);
const [teachers, setTeachers] = useState([]);
const [error, setError] = useState(null);
const navigate = useNavigate();
const [rating, setRating] = useState("");
  const [activeTab, setActiveTab] = useState("lessons"); 

useEffect(() => {
      
      const fetchRequests = async () =>{
      try{
        
      const res = await axios.get("http://localhost:8081/student/allrequests", { withCredentials: true })
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
        
      const res = await axios.get("http://localhost:8081/student/alllessons", { withCredentials: true })
     setLessons(res.data);
     console.log("lessons");
     console.log(res.data);
     console.log("aktivni");
     console.log(res.data.filter.active)
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
    console.log(request.studentId);

     try{
            const response = await axios.patch(  `http://localhost:8081/student/request/${id}`,{}, {withCredentials: true});
            console.log(response);
           
              
            
        } catch (e) {
            console.error("Greška pri deaktiviranju zahtjeva ", e);
        };
    
  };

  const handleDecline =  async(id) =>{
      setLessons(prev => prev.filter(lesson => lesson.lessonId !== id));

    
     try{
            const response = await axios.patch(  `http://localhost:8081/student/lesson/${id}`,{}, {withCredentials: true});
            console.log(response);
           
              
            
        } catch (e) {
            console.error("Greška pri deaktiviranju zahtjeva ", e);
        };

  }


  const seen =  async(id) =>{
      setLessons(prev => prev.filter(lesson => lesson.lessonId !== id));

    
     try{
            const response = await axios.patch(  `http://localhost:8081/student/info/${id}`,{}, {withCredentials: true});
            console.log(response);
           
              
            
        } catch (e) {
            console.error("Greška pri postavljanju statusa ", e);
        };

  }

    const rate =  async(id, lid, rating) =>{
      if(rating !== ''){
      setLessons(prev => prev.filter(lesson => lesson.lessonId !== lid));
      console.log(lid);
    
     try{
            const response = await axios.patch(  `http://localhost:8081/student/rate/${id}/${rating}`,{}, {withCredentials: true});
            console.log(response);
           
              
            
        } catch (e) {
            console.error("Greška pri postavljanju ocjene ", e);
        };
             try{
            const response = await axios.patch(  `http://localhost:8081/student/info/${lid}`,{}, {withCredentials: true});
            console.log(response);
           
              
            
        } catch (e) {
            console.error("Greška pri postavljanju statusa ", e);
        };

  }
  else{
     alert("Unesite ocijenu između 1 i 5.");
  }
}

const handleTabChange = (tab) => setActiveTab(tab);


 return (
  <><Header /><div className="container mt-4 mb-4">
 
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
           className={`nav-link ${activeTab === "feedback" ? "active" : ""}`}
           onClick={() => handleTabChange("feedback")}
         >
           Povratne informacije
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
             {lessons.filter(l => l.active).sort((a, b) => new Date(a.dateTime) - new Date(b.dateTime)).map(lesson => (
               <div
                 key={lesson.lessonId}
                 className="p-4 border rounded-lg shadow bg-white"
               >
                 <h3 className="font-bold text-lg mb-2">
                   {lesson.subject} ({lesson.type})
                 </h3>
                 <p><strong>Instruktor:</strong> {lesson.teacherName}</p>
                 <p><strong>Datum:</strong> {new Date(lesson.dateTime).toLocaleString()}</p>
                 <p><strong>Lokacija:</strong> {lesson.address}</p>
                 <p><strong>Trajanje:</strong> {lesson.duration} minuta</p>
                 <p><strong>Cijena:</strong> {lesson.price}€</p>
                 <p><strong>Gradivo:</strong> {lesson.description}</p>
                 <button
                   className="btn btn-danger btn-sm mt-2"
                   onClick={() => handleDecline(lesson.lessonId)}
                 >
                   Otkaži
                 </button>
               </div>
             ))}
           </div>
         </div>
       )}

       {activeTab === "feedback" && (
         <div>
           <h2 className="mb-3">Povratne informacije</h2>
           <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
             {lessons.filter(l => l.info != null && !l.seen).map(lesson => (
               <div
                 key={lesson.lessonId}
                 className="p-4 border rounded-lg shadow bg-white"
               >
                 <h3 className="font-bold text-lg mb-2">{lesson.subject}</h3>
                 <p><strong>Instruktor:</strong> {lesson.teacherName}</p>
                 <p><strong>Datum:</strong> {new Date(lesson.dateTime).toLocaleString()}</p>
                 <p className="italic"><strong>Gradivo:</strong> {lesson.description}</p>
                 <p><strong>Povratna informacija:</strong> {lesson.info}</p>

                
                 <div className="flex items-center flex-wrap gap-2 mt-3">
                   <button
                     className="btn btn-danger btn-sm mb-3"
                     onClick={() => seen(lesson.lessonId)}
                   >
                     Viđeno
                   </button>

               
                   <input
                     type="number"
                     min="1"
                     max="5"
                     step="1"
                     value={rating}
                     className="form-control form-control-sm w-25 mb-2 text-center"
                     onChange={(e) => {
                       let value = parseInt(e.target.value, 10);
                       if (isNaN(value)) {
                         setRating("");
                         return;
                       }
                       value = Math.min(Math.max(value, 1), 5);
                       setRating(value);
                     } } />

                   <button
                     className="btn btn-danger btn-sm"
                     onClick={() => rate(lesson.teacherID, lesson.lessonId, rating)}
                   >
                     Ocijeni
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
             {requests.filter(request => request.active).map(r => (
               <div
                 key={r.id}
                 className="p-4 border rounded-lg shadow bg-white"
               >
                 <h3 className="font-bold text-lg mb-2">{r.subject}</h3>
                 <p><strong>Gradivo:</strong> {r.description}</p>
                 <p><strong>Instruktor:</strong> {r.person}</p>
                 <button
                   onClick={() => handleDecision(r.id, "declined", r)}
                   className="btn btn-danger btn-sm mt-2"
                 >
                   Otkaži
                 </button>
               </div>
             ))}
           </div>
         </div>
       )}
     </div>
   </div></>
);
}

export default StudentHome;

