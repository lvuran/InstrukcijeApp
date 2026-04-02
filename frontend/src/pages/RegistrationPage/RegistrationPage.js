import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom"; 
import axios from "axios";
import { useState, useEffect } from "react";
import Header from "../../components/Header/Header";

function RegistrationPage() {
  const navigate = useNavigate();

  const [address, setAddress] = useState("");
  const [location, setLocation] = useState("");
  const [telephone, setTelephone] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [grade, setGrade] = useState("");
  const [locations, setLocations] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const res = await axios.get("http://localhost:8081/location/all", { withCredentials: true });
        setLocations(res.data);
      } catch (error) {
        console.error("Greška:", error);
        setError("Nije moguće dohvatiti lokacije");
      }
    };
    fetchLocations();
  }, []);

  const Register = async (e) => {
    e.preventDefault();
    const selectedLocation = locations.find(l => l.id === Number(location));

    const studentDTO = {
      address,
      location: selectedLocation,
      telephone,
      subjects,
      grade
    };

    try {
      const response = await axios.patch("http://localhost:8081/user/student/register", studentDTO, { withCredentials: true });
      console.log(response);
      navigate('/Home');
    } catch (e) {
      console.error("Greška pri registraciji: ", e);
    }
  };

  const isFormValid = () => {
    return (
      address.trim() !== "" &&
      location !== "" &&
      telephone.trim() !== "" &&
      grade > 0 &&
      subjects.length > 0
    );
  };

  return (
    <><Header /><div className="container mt-5">
      <h2 className="mb-4">Registracija</h2>
      <p>Dobrodošli, ispunite ove podatke za registraciju</p>

      {error && <div className="alert alert-danger">{error}</div>}

      <form className="row g-3" onSubmit={Register}>
      
        <div className="col-md-6">
          <label htmlFor="address" className="form-label">Adresa</label>
          <input
            type="text"
            className={`form-control ${address.trim() === "" ? "is-invalid" : "is-valid"}`}
            id="address"
            value={address}
            onChange={(e) => setAddress(e.target.value)} />
          {address.trim() === "" && <div className="invalid-feedback">Adresa je obavezna.</div>}
        </div>
        <br></br>
  
        <div className="col-md-6">
          <label htmlFor="location" className="form-label">Lokacija</label>
          <select
            id="location"
            className={`form-select ${location === "" ? "is-invalid" : "is-valid"}`}
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          >
            <option value="">Odaberi lokaciju</option>
            {locations.map(loc => (
              <option key={loc.id} value={loc.id}>{loc.name}</option>
            ))}
          </select>
          {location === "" && <div className="invalid-feedback">Lokacija je obavezna.</div>}
        </div>
        <br></br>
   
        <div className="col-md-6">
          <label htmlFor="telephone" className="form-label">Telefonski broj</label>
          <input
            type="tel"
            className={`form-control ${telephone.trim() === "" ? "is-invalid" : "is-valid"}`}
            id="telephone"
            value={telephone}
            onChange={(e) => setTelephone(e.target.value)} />
          {telephone.trim() === "" && <div className="invalid-feedback">Unesite telefonski broj.</div>}
        </div>
        <br></br>
   
        <div className="col-md-6">
          <label htmlFor="grade" className="form-label">Razred</label>
          <input
            type="number"
            min="1"
            className={`form-control ${grade > 0 ? "is-valid" : "is-invalid"}`}
            id="grade"
            value={grade}
            onChange={(e) => setGrade(e.target.value)} />
          {grade <= 0 && <div className="invalid-feedback">Razred mora biti pozitivan broj.</div>}
        </div>
        <br></br>
   
        <div className="col-12">
          <p className="fw-bold">Predmeti koje trebam:</p>
          <div className="row">
            {[
              "HRVATSKI", "MATEMATIKA", "ENGLESKI", "PRIRODA", "BIOLOGIJA",
              "FIZIKA", "KEMIJA", "TEHNICKI", "POVIJEST", "GEOGRAFIJA",
              "INFORMATIKA", "GLAZBENI", "LIKOVNI"
            ].map((subj, index) => (
              <div className="col-md-4" key={index}>
                <div className="form-check">
                  <input
                    className="form-check-input"
                    type="checkbox"
                    value={subj}
                    id={`subject${index}`}
                    checked={subjects.includes(subj)}
                    onChange={(e) => {
                      if (e.target.checked) {
                        setSubjects([...subjects, e.target.value]);
                      } else {
                        setSubjects(subjects.filter((s) => s !== e.target.value));
                      }
                    } } />
                  <label className="form-check-label" htmlFor={`subject${index}`}>
                    {subj.charAt(0) + subj.slice(1).toLowerCase()}
                  </label>
                </div>
              </div>
            ))}
          </div>
          {subjects.length === 0 && <div className="text-danger mt-1">Odaberite barem jedan predmet.</div>}
        </div>

       
        <div className="col-12 mb-4">
          <button type="submit" className="btn btn-primary px-4 mb-4" disabled={!isFormValid()}>
            Spremi
          </button>
        </div>
      </form>
    </div></>
  );
}

export default RegistrationPage;
