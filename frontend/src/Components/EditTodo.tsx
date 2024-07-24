import {FormEvent, useState} from "react";
import "./CreateTodo.css"
import {Todo} from "../Types/Todo.ts";
import {Params, useNavigate, useParams} from "react-router-dom";
import {TodoStatus} from "../Types/TodoStatus.ts";

type TodoCardProps = {
    todos:Todo [];
    editTodo: (id:string, todo:Todo) => void;
}

export default function EditTodo(props: Readonly<TodoCardProps>) {

    const { editTodo, todos} = props;
    const urlParams: Readonly<Params<string>> = useParams();
    const id: string = urlParams.id || "";

    const currentTodo: Todo | undefined = todos.find(todo => todo.id === urlParams.id);

    const [newDescription, setNewDescription] = useState<string>(currentTodo ? currentTodo.description : "");
    const [newStatus, setNewStatus] = useState<TodoStatus>(currentTodo ? currentTodo.status : "OPEN");
    const navigate = useNavigate();


    function handleSubmit(event:FormEvent<HTMLFormElement> ) {
        event.preventDefault();
            editTodo(id, {id:id, description:newDescription, status:newStatus});
        console.log("You updated Todo with Id " + id);
        }

    function handleCancel() {
        navigate("/todo/home")
    }


    return (
        <>
            <form onSubmit={(event) => handleSubmit(event)}>

                    <label >Description:</label>
                    <input
                        type="text"
                        value={newDescription}
                        onChange={(e) => setNewDescription(e.target.value)}
                    />


                    <label >Status:</label>
                    <select
                        id="status"
                        value={newStatus}
                        onChange={(e) => setNewStatus(e.target.value as TodoStatus)}
                    >
                        <option value="OPEN">Open</option>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="DONE">Done</option>
                    </select>

                <button type="submit">Save</button>
                <button type="button" onClick={handleCancel}>Cancel</button>    
            </form>


        </>
    )
}