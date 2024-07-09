import TodoCard from "./TodoCard.tsx";
import {useEffect, useState} from "react";
import "./TodoGallery.css"
import {Todo} from "../Types/Todo.ts";

type TodoGalleryProps = {
    todos: Todo[];
    updateTodoStatus: (id: string, description: string, status: 'OPEN' | 'IN_PROGRESS' | 'DONE') => void;
    deleteTodo: (id: string) => void;
}

export default function TodoGallery({todos, updateTodoStatus, deleteTodo}: TodoGalleryProps) {
    const [openTodos, setOpenTodos] = useState<Todo[]>([]);
    const [inProgressTodos, setInProgressTodos] = useState<Todo[]>([]);
    const [doneTodos, setDoneTodos] = useState<Todo[]>([]);

    useEffect(() => {
        const open = todos.filter(todo => todo.status === 'OPEN');
        const inProgress = todos.filter(todo => todo.status === 'IN_PROGRESS');
        const done = todos.filter(todo => todo.status === 'DONE');

        setOpenTodos(open);
        setInProgressTodos(inProgress);
        setDoneTodos(done);
    }, [todos]);

    return (
        <div className="Gallery">
            <div className="todo-list">
                <h3>Open</h3>
                {openTodos.length > 0 && (
                    <div>
                        {openTodos.map(todo => (
                            <TodoCard
                                key={todo.id}
                                id={todo.id}
                                description={todo.description}
                                status={todo.status}
                                updateStatus={updateTodoStatus}
                                deleteTodo={deleteTodo}
                            />
                        ))}
                    </div>
                )}
            </div>

            <div className="todo-list">
                <h3>In Progress</h3>
                {inProgressTodos.length > 0 && (
                    <div>
                        {inProgressTodos.map(todo => (
                            <TodoCard
                                key={todo.id}
                                id={todo.id}
                                description={todo.description}
                                status={todo.status}
                                updateStatus={updateTodoStatus}
                                deleteTodo={deleteTodo}
                            />
                        ))}
                    </div>
                )}</div>


            <div className="todo-list">
                <h3>Done</h3>
                {doneTodos.length > 0 && (
                    <div>

                        {doneTodos.map(todo => (
                            <TodoCard
                                key={todo.id}
                                id={todo.id}
                                description={todo.description}
                                status={todo.status}
                                updateStatus={updateTodoStatus}
                                deleteTodo={deleteTodo}
                            />
                        ))}
                    </div>
                )}
            </div>
        </div>
    )
}
