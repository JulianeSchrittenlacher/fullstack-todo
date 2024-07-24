
import {FormEvent, useState} from "react";
import "./CreateTodo.css"
import {Todo} from "../Types/Todo.ts";

export default function CreateTodo({addTodo}:{addTodo: (todo:Todo)=> void}) {
    const [description, setDescription] = useState<string>("");

    function handleSubmit(event:FormEvent<HTMLFormElement> ) {
        event.preventDefault();
        const newTodo:Todo = {
            id:"",
            description,
            status:'OPEN',
        };

        addTodo(newTodo);
        setDescription('');
        {/*alert("A new Todo was created: " + description)*/}
    }

    return (
        <>
            <form className="AddForm" onSubmit={handleSubmit}>
                <label>
                    <input type={"text"}
                    placeholder={"Enter your Todo"}
                    value={description}
                    onChange={(e)=>setDescription(e.target.value)}/>
                </label>
                <button>Create</button>
            </form>
        </>
    )
}