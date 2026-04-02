import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import RegistationPage from './pages/RegistrationPage/RegistrationPage';
import SearchPage from './pages/SearchPage/SearchPage';
import WelcomePage from './pages/WelcomePage/WelcomePage';
import RolePage from './pages/RolePage/RolePage';
import TeacherRegister from './pages/TeacherRegister/TeacherRegister';
import TeacherHome from './pages/TeacherHome/TeacherHome'
import CreateLesson from './pages/CreateLesson/CreateLesson';
import StudentHome from './pages/StudentHome/StudentHome';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  return (
    <Router>
      <Routes>
       
        <Route path="/registration" element={<RegistationPage/>}/>
        <Route path='/search' element={<SearchPage/>}/>
        <Route path='/' element={<WelcomePage/>}/>
       <Route path='/role' element={<RolePage/>}/> 
       <Route path='/tregistration' element={<TeacherRegister/>}/>
       <Route path='/teacherHome' element={<TeacherHome/>}/>
       <Route path='/createLesson' element={<CreateLesson/>}/>
       <Route path='/home' element={<StudentHome/>}/>
      </Routes>
    </Router>
  );
}

export default App;
