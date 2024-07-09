import "./TodoCard.css"
import {Todo} from "../Types/Todo.ts";
import {TodoStatus} from "../Types/TodoStatus.ts";

type TodoCardProps = Todo & {
    updateStatus: (id: string, description:string, status: TodoStatus) => void,
    deleteTodo: (id:string) => void,
}
export default function TodoCard({ id, description, status, updateStatus, deleteTodo}: TodoCardProps) {
    return (
        <div className="TodoCard"  >
            <p>{description}</p>
            <div className="button-container">
                <button onClick={() => updateStatus(id, description, status)}>Advance</button>
                <button onClick={() => deleteTodo(id)}>Delete</button>
            </div>

        </div>
    )
}