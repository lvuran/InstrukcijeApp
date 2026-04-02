import { useNavigate } from "react-router-dom"; 
import axios from "axios";
import { useState, useEffect } from "react";
import Header from "../../components/Header/Header";

function TeacherRegister() {
  const navigate = useNavigate();

  const [address, setAddress] = useState("");
  const [location, setLocation] = useState("");
  const [telephone, setTelephone] = useState("");
  const [about, setAbout] = useState("");
  const [subjects, setSubjects] = useState([]);
  const [duration, setDuration] = useState("");
  const [price, setPrice] = useState("");
  const [type, setType] = useState("");
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

  const isFormValid = () => {
    return (
      address.trim() !== "" &&
      location.trim() !== "" &&
      telephone.trim() !== "" &&
      about.trim() !== "" &&
      subjects.length > 0 &&
      duration > 0 &&
      price > 0 &&
      type !== ""
    );
  };

  const Register = async (e) => {
    e.preventDefault();
    if (!isFormValid()) return;

    const selectedLocation = locations.find((l) => l.id === Number(location));

    const teacherDTO = {
      address,
      location: selectedLocation,
      telephone,
      about,
      subjects,
      duration: parseInt(duration),
      price: parseInt(price),
      type,
    };

    try {
      const response = await axios.patch(
        "http://localhost:8081/user/teacher/register",
        teacherDTO,
        { withCredentials: true }
      );
      console.log(response);
      navigate("/teacherHome");
    } catch (e) {
      console.error("Greška pri registraciji: ", e);
    }
  };

  return (
    <><Header /><div className="container mt-5 mb-5">
      <h1 className="mb-3">Registracija</h1>
      <p className="text-muted">Dobrodošli, ispunite ove podatke:</p>

      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={Register} className="row g-3">

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
            className={`form-select ${location.trim() === "" ? "is-invalid" : "is-valid"}`}
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          >
            <option value="">Odaberi lokaciju</option>
            {locations.map((loc) => (
              <option key={loc.id} value={loc.id}>{loc.name}</option>
            ))}
          </select>
          {location.trim() === "" && <div className="invalid-feedback">Odaberite lokaciju.</div>}
        </div><br></br>

       
        <div className="col-md-6">
          <label htmlFor="telephone" className="form-label">Telefonski broj</label>
          <input
            type="tel"
            className={`form-control ${telephone.trim() === "" ? "is-invalid" : "is-valid"}`}
            id="telephone"
            value={telephone}
            onChange={(e) => setTelephone(e.target.value)} />
          {telephone.trim() === "" && <div className="invalid-feedback">Telefonski broj je obavezan.</div>}
        </div>

     
        <div className="col-12">
          <label htmlFor="about" className="form-label">O meni</label>
          <textarea
            className={`form-control ${about.trim() === "" ? "is-invalid" : "is-valid"}`}
            id="about"
            rows="3"
            value={about}
            onChange={(e) => setAbout(e.target.value)}
          ></textarea>
          {about.trim() === "" && <div className="invalid-feedback">O meni je obavezno.</div>}
        </div>

      
        <div className="col-12">
          <p className="fw-bold">Predmeti koje podučavam:</p>
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

   
        <div className="col-md-6">
          <label htmlFor="duration" className="form-label">Trajanje poduke (u min)</label>
          <input
            type="number"
            min="0"
            className={`form-control ${duration > 0 ? "is-valid" : "is-invalid"}`}
            id="duration"
            value={duration}
            onChange={(e) => setDuration(e.target.value)} />
          {duration <= 0 && <div className="invalid-feedback">Trajanje mora biti veće od 0.</div>}
        </div>
        <br></br>
     
        <div className="col-md-6">
          <label htmlFor="price" className="form-label">Cijena poduke (€)</label>
          <input
            type="number"
            min="0"
            className={`form-control ${price > 0 ? "is-valid" : "is-invalid"}`}
            id="price"
            value={price}
            onChange={(e) => setPrice(e.target.value)} />
          {price <= 0 && <div className="invalid-feedback">Cijena mora biti veća od 0.</div>}
        </div><br></br>

      
        <div className="col-12">
          <p className="fw-bold">Način održavanja</p>
          {["ONLINE", "OFFLINE"].map((option) => (
            <div className="form-check form-check-inline" key={option}>
              <input
                className="form-check-input"
                type="radio"
                id={option}
                name="type"
                value={option}
                checked={type === option}
                onChange={(e) => setType(e.target.value)} />
              <label className="form-check-label" htmlFor={option}>
                {option === "ONLINE" ? "Online" : option === "OFFLINE" ? "Uživo" : ""}
              </label>
            </div>
          ))}
          {type === "" && <div className="text-danger mt-1">Odaberite način održavanja.</div>}
        </div>

      
        <div className="col-12">
          <button type="submit" className="btn btn-primary px-4" disabled={!isFormValid()}>
            Spremi
          </button>
        </div>
      </form>
    </div></>
  );
}

export default TeacherRegister;
