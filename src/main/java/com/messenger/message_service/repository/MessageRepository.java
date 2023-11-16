package com.messenger.message_service.repository;

import com.messenger.message_service.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {

    @Query("SELECT MAX(id) FROM MessageModel WHERE groupId = :groupId")
    Long findMaxIdByGroup(Long groupId);

    @Query("SELECT MIN(id) FROM MessageModel WHERE groupId = :groupId")
    Long findMinIdByGroup(Long groupId);

    List<MessageModel> findAllByGroupIdOrderBySendDatetimeAsc(Long groupId);

    @Query("SELECT m " +
            "FROM MessageModel m " +
            "WHERE m.groupId = :groupId AND m.id > :offsetId " +
            "ORDER BY m.sendDatetime DESC")
    List<MessageModel> searchAllMessagesInGroupStartFromOffset(Long groupId, Long offsetId);
}
