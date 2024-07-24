import './App.css'
import {useEffect, useState} from "react";
import axios from "axios";
import {NavLink, Route, Routes, useNavigate} from "react-router-dom";
import CreateTodo from "./Components/CreateTodo.tsx";
import TodoGallery from "./Components/TodoGallery.tsx";
import Header from "./Components/Header.tsx";
import {TodoStatus} from "./Types/TodoStatus.ts";
import {Todo} from "./Types/Todo.ts";
import ProtectedRoute from "./Components/ProtectedRoute.tsx";
import EditTodo from "./Components/EditTodo.tsx";

export default function App() {
  const [todos,setTodos] = useState<Todo[]>([]);
  {/*if (!todos) {
    return console.log("Lade...");

  } */}
  const [user,setUser] = useState<string |undefined | null>(undefined)

  const navigate = useNavigate();

  function getTodos() {
    axios.get("/api/todo")
        .then(response => setTodos(response.data))
        .catch(error => console.log(error))
  }

  function addTodo(newTodo:  Todo) {

    axios.post("/api/todo", newTodo)
        .then(() => {
          getTodos();
          navigate('/todo/home');
        })
        .catch(error => console.log(error));
  }

  function updateTodoStatus(id: string, description:string, status: TodoStatus) {

   const newStatus = getNextStatus(status);

    axios.put(`/api/todo/${id}`, { id:id, description:description, status:newStatus })
        .then(getTodos)
        .catch(error => {
          console.error('Error updating todo status:', error)
        });
  }

  function getNextStatus(status: TodoStatus): TodoStatus {
    switch (status) {
      case 'OPEN': return 'IN_PROGRESS';
      case 'IN_PROGRESS': return 'DONE';
      case "DONE": return 'DONE'
    }
  }

  function deleteTodo(id:string) {
    axios.delete(`/api/todo/${id}`)
        .then(getTodos)
        .catch(error => console.log(error));
  }

function editTodo(id:string, updatedTodo:Todo) {
      navigate('todo/')
      axios.put(`/api/todo/${id}`, updatedTodo)
          .then(() => {
              getTodos();
              navigate('/todo/home');
          })
          .catch(error => console.error("Error updating Todo: ", error));
}

  const login = () => {
    const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
    const loginUrl = `${host}/oauth2/authorization/github?redirect_uri=${encodeURIComponent(window.location.origin + '/todo/home')}`;

    window.open(loginUrl, '_self')
  }

  const logout = () => {
    const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
    const logoutUrl = `${host}/logout?redirect_uri=${encodeURIComponent(window.location.origin + '/todo/home')}`;//das funktioniert noch nicht!

    window.open(logoutUrl, '_self')
  }

  const me = () => {
    axios.get("/api/auth/me")
        .then(response => {
          setUser(response.data)
        })
        .catch(() => {
          setUser(null)
        })
  }

  useEffect(getTodos, [])

  useEffect(() => {
    me()
  }, []);


  if (!todos) {
    return "Lade..."
  }

  return (
      <>
        <div className="Header">
          <Header/>
        </div>
        <div className="login-logout-buttons">
          {user === null ? (
              <button onClick={login}>Login</button>
          ) : (
              <button onClick={logout}>Logout</button>
          )}
          <p>User: {user}</p>
        </div>

        <div className="App">
          <nav className="App-nav">
              <ul>
                  <li>
                      <NavLink to="/todo/home" className={({isActive}) => (isActive ? "active-link" : "")}>
                          Home
                      </NavLink>
                  </li>
                  <li>
                      <NavLink to="/todo/create" className={({isActive}) => (isActive ? "active-link" : "")}>
                          Add Todo
                      </NavLink>
                  </li>
              </ul>
          </nav>
            <Routes>
                <Route element={<ProtectedRoute user={user}/>}/>
                <Route path="/todo/home" element={<TodoGallery todos={todos} updateTodoStatus={updateTodoStatus} deleteTodo={deleteTodo} editTodo={editTodo}/>}/>
                <Route path="/todo/create" element={<CreateTodo addTodo={addTodo}/>}/>
                <Route path="/todo/edit/:id" element={<EditTodo todos={todos} editTodo={editTodo}/>}/>
                <Route path="/profile" element={<p>{user}</p>}/>
          </Routes>
        </div>
      </>
  )
}


