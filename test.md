Uri: 
ws://localhost:1000/ws (STOMP)

Subscription:
/invite_to_group/1
/group/1


psql -U postgres -d messenger

SELECT relreplident FROM pg_class WHERE relname = 'groups';
SELECT relreplident FROM pg_class WHERE relname = 'invitations_table';
SELECT relreplident FROM pg_class WHERE relname = 'group_memberships_table';
SELECT relreplident FROM pg_class WHERE relname = 'lastseen';
SELECT relreplident FROM pg_class WHERE relname = 'message_table';

ALTER TABLE public.Groups REPLICA IDENTITY FULL;
ALTER TABLE public.Invitations_Table REPLICA IDENTITY FULL;
ALTER TABLE public.Group_Memberships_Table REPLICA IDENTITY FULL;

два раза перезапустить докер компосе файл 


