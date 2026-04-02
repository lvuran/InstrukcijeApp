import "./SearchPage.css";

import { useNavigate } from "react-router-dom"; 
import axios from "axios";
import { useState } from "react";
import { useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Header from "../../components/Header/Header";

function SearchPage() {

    const[subject, setSubject] = useState("");
    const [location, setLocation] = useState("");
    const[teacher, setTeacher] = useState("");
const [locations, setLocations] = useState([]);
const [error, setError] = useState(null);
const  [teachers, setTeachers] = useState([]);
const [subjects, setSubjects] = useState([]);
const [price, setPrice] = useState("");
const [type, setType] = useState("");
const [selected, setSelected] = useState(false);
const [selectedSubject, setSelectedSubject] = useState("");
const [description, setDescription] = useState("");
const [searchName, setSearchName] = useState("");



useEffect(() => {
  
  const fetchLocations = async () =>{
  try{
    
  const res = await axios.get("http://localhost:8081/location/all", { withCredentials: true })
 setLocations(res.data);
  }
  catch (error) {
                console.error("Greška: ", error);
                setError("Nije moguće dohvatiti lokacije");
  }

};
const fetchTeachers = async () => {

    try{
        const res = await axios.get("http://localhost:8081/teacher/all", {withCredentials: true})
        setTeachers(res.data);
        console.log(res.data);
    }
    catch (error){
        console.error("Greška: ", error);
        setError("NIje moguće dohvatiti instruktore");
    }

};
const fetchSubjects = async () => {

    try{
        const res = await axios.get("http://localhost:8081/student/subjects", {withCredentials: true})
        setSubjects(res.data);
        console.log(res.data);
    }
    catch (error){
        console.error("Greška: ", error);
        setError("NIje moguće dohvatiti predmete");
    }

};
  fetchLocations();
    fetchTeachers();
fetchSubjects();}, []);


const filteredTeachers = teachers.filter(t => {
 
  const matchesSubject = subject === "" || t.subjects.includes(subject);

  const matchesPrice = price === "" || t.price <= parseFloat(price);

 const matchesType = type === "" || t.type === type;
  const matchesName = searchName === "" || 
                      t.username.toLowerCase().includes(searchName.toLowerCase());

  return matchesSubject && matchesPrice && matchesType && matchesName;

});

const openRequest = async(id) => {  setSelected(true);
const foundTeacher = teachers.find(t => t.id === id);  
  setTeacher(foundTeacher);
  setSelectedSubject(subject);
   if (foundTeacher.subjects.length === 1) {
    setSelectedSubject(foundTeacher.subjects[0]);
  } else {
    setSelectedSubject(""); 
  }

}

    const sendRequest =  async ()  => {
     
        const requestDTO = {
            teacherId: teacher.id,
            subject :selectedSubject,
            description
        };
      
        try{
          console.log(requestDTO);
            const response = await axios.post("http://localhost:8081/teacher/request", requestDTO, {withCredentials: true});
            console.log(response);
           
        } catch (e) {
            console.error("Greška u slanju zahtjeva: ", e);
        };
        setSelected(false);
    }


 return (
  <><Header></Header><div className="container mt-4">
     <h1 className="mb-4">Pretraga</h1>

     {selected && teacher && (
       <div className="request-panel">
         <h5 className="mb-3">{teacher.username}</h5>
         <p><strong>Cijena:</strong> {teacher.price} €</p>

       
         <div className="mb-3">
           {teacher.subjects.length === 1 ? (
             <span className="badge bg-primary">{teacher.subjects[0]}</span>
           ) : (
             <select
               id="subject"
               name="subject"
               className="form-select form-select-sm"
               value={selectedSubject}
               onChange={(e) => setSelectedSubject(e.target.value)}
             >
               <option value="">Odaberi predmet</option>
               {teacher.subjects.map((subj) => (
                 <option key={subj} value={subj}>{subj}</option>
               ))}
             </select>
           )}
         </div>

        
         <div className="mb-3">
           <label htmlFor="description" className="form-label">Sadržaj lekcije:</label>
           <input
             type="text"
             id="description"
             className="form-control form-control-sm"
             placeholder="Unesite opis lekcije"
             value={description}
             onChange={(e) => setDescription(e.target.value)} />
         </div>

     
         <button
           className="btn btn-success w-100 mb-2"
           onClick={() => sendRequest()}
         >
           Pošalji zahtjev
         </button>

         <button
           className="btn btn-outline-secondary w-100"
           onClick={() => setSelected(false)}
         >
           Zatvori
         </button>
       </div>
     )}

 
     <form className="mb-4">
       <div className="row g-3 align-items-end">
         <div className="col-md-4">
           <label htmlFor="subject" className="form-label">Predmet</label>
           <select
             className="form-select"
             id="subject"
             value={subject}
             onChange={(e) => setSubject(e.target.value)}
           >
             <option value="">Odaberi</option>
             {subjects.map((loc) => (
               <option key={loc} value={loc}>
                 {loc}
               </option>
             ))}
           </select>
         </div>

         <div className="col-md-3">
           <label htmlFor="price" className="form-label">Cijena do</label>
           <input
             type="number"
             className="form-control"
             id="price"
             value={price}
             onChange={(e) => setPrice(e.target.value)} />
         </div>

         <div className="col-md-5">
           <label htmlFor="searchName" className="form-label">Pretraži po imenu</label>
           <input
             type="text"
             className="form-control"
             id="searchName"
             value={searchName}
             onChange={(e) => setSearchName(e.target.value)} />
         </div>
       </div>

       <div className="mt-3">
         <label className="form-label">Način održavanja</label>
         <div className="form-check">
           <input
             type="radio"
             className="form-check-input"
             id="ONLINE"
             name="type"
             value="ONLINE"
             checked={type === "ONLINE"}
             onChange={(e) => setType(e.target.value)} />
           <label className="form-check-label" htmlFor="ONLINE">Online</label>
         </div>

         <div className="form-check">
           <input
             type="radio"
             className="form-check-input"
             id="OFFLINE"
             name="type"
             value="OFFLINE"
             checked={type === "OFFLINE"}
             onChange={(e) => setType(e.target.value)} />
           <label className="form-check-label" htmlFor="OFFLINE">Uživo</label>
         </div>

    
       </div>
     </form>

   
     <h3 className="mb-3">Instruktori</h3>
     <div className="row">
       {filteredTeachers.length === 0 ? (
         <p>Nema instruktora</p>
       ) : (
         filteredTeachers.map((t) => (
           <div
             key={t.id}
             className="card mb-3 shadow-sm"
           >
             <div className="card-body">
               <h5 className="card-title">{t.username}</h5>
               <p className="card-text"><strong>Ocjena:</strong> {t.ocjena}/5 ({t.ratings} ocjena)</p>
               <p className="card-text"><strong>Kontakt broj:</strong> {t.phone}</p>
               <p className="card-text"><strong>E-pošta:</strong> {t.mail}</p>
               <p className="card-text"><strong>O instruktoru:</strong> {t.about}</p>
               <p className="card-text"><strong>Predmeti:</strong> {t.subjects.join(", ")}</p>
               <p className="card-text"><strong>Tip:</strong> {t.type}</p>
               <p className="card-text"><strong>Cijena:</strong> {t.price} €</p>

               <p className="card-text"><strong>Trajanje:</strong> {t.duration} min</p>

               <button
                 className="btn btn-outline-primary btn-sm"
                 onClick={() => openRequest(t.id)} 
               >
                 Pošalji zahtjev
               </button>
             </div>
           </div>
         )))}
     </div>
   </div></>
);
}

export default SearchPage;