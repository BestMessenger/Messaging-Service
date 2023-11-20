package com.messenger.message_service.models;

import com.messenger.message_service.utils.enums.MessageTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Message_Table")
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageTypeEnum messageType;

    @Column(name = "message")
    private String message;

    @Column(name = "groupId")
    private Long groupId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "send_datetime")
    private LocalDateTime sendDatetime;

    @Column(name = "file_uri")
    private String fileUrl;
}
