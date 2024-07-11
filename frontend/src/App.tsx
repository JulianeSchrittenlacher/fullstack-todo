import './App.css'
import {useEffect, useState} from "react";
import axios from "axios";
import {NavLink, Route, Routes, useNavigate} from "react-router-dom";
import CreateTodo from "./Components/CreateTodo.tsx";
import TodoGallery from "./Components/TodoGallery.tsx";
import Header from "./Components/Header.tsx";
import {TodoStatus} from "./Types/TodoStatus.ts";
import {Todo} from "./Types/Todo.ts";

export default function App() {
  const [todos,setTodos] = useState<Todo[]>([]);
  {/*if (!todos) {
    return console.log("Lade...");

  } */}

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
  
  useEffect(()=> {
    getTodos()
  },[])

  return (
      <>
        <div className="Header">
          <Header></Header>
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
        <Route path="/todo/home" element={<TodoGallery todos={todos} updateTodoStatus={updateTodoStatus} deleteTodo={deleteTodo}/>}/>
        <Route path="/todo/create" element={<CreateTodo addTodo={addTodo}/>}/>
      </Routes>
    </div>
      </>
  )
}


