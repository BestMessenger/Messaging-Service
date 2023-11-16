Uri: 
ws://localhost:1000/ws

Subscription:
/group/1
/error/1

Main Controller:
/app/message

{
"action": "SEND_GROUP_MESSAGE",
"sender_id": 1,
"group_id": 1,
"message": "Hello?"
}

{
"action": "FETCH_GROUP_MESSAGES",
"sender_id": 1,
"group_id": 1,
"message": null
}

{
"action": "LEAVE_GROUP",
"sender_id": 1,
"group_id": 1,
"message": null
}

{
"action": "NOTIFICATION_MESSAGE",
"sender_id": 1,
"group_id": 1,
"message": "Warning!"
}

{
"action": "CHANGE_ADMIN",
"sender_id": 1,
"group_id": 1,
"message": null
}

