package com.example.news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "friendship")
public class Friendship extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    Status status;

    public Friendship() {
    }

    public enum Status {
        REQUESTED,
        ACCEPTED,
        REJECTED
    }

    public Friendship(User requester, User receiver, Status status) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = status;
    }

}
