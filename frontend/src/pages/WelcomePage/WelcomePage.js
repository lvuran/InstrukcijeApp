import Header from "../../components/Header/Header";
import "./WelcomePage.css";

function WelcomePage(){

 function handleGoogleLogin() {
       
        window.location.href = "http://localhost:8081/oauth2/authorization/google"
     }

return( <><Header></Header><div className="container text-center mt-5">
   <h1 className="mb-3"><span className="text-primary">Dobrodošli</span></h1>
   <p className="lead mb-4">
      Pronađite instruktora, dogovorite lekcije, dobijte feedback – sve na jednom mjestu.
   </p>

   <button
      onClick={handleGoogleLogin}
      className="btn btn-light border d-flex align-items-center mx-auto px-4 py-2"
      style={{ maxWidth: "300px" }}
   >
      <img
         src="https://www.gstatic.com/firebasejs/ui/2.0.0/images/auth/google.svg"
         alt="Google logo"
         style={{ width: "20px", height: "20px", marginRight: "10px" }} />
      <span className="fw-medium">Prijavi se sa Google računom</span>
   </button>
</div></>
)
}
export default WelcomePage;