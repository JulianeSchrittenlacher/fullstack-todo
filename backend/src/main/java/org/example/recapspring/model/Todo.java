package org.example.recapspring.model;

import lombok.AllArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;


@With

public record Todo(@Id String id,
                   String description,
                   Status status) {
//    public Todo todoUpdated(String newDescription, Status newStatus) {
//        return new Todo(
//                this.id(),
//                newDescription,
//                newStatus
//        );


}
