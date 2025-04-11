-- Création des tables
@tables_script/create_tables.sql

-- Séquences
@sequences_script/reservation_id_seq.sql
@sequences_script/employee_id_seq.sql

-- Ajout de données ou procédures
@client_script/add_client.sql
@room_script/add_room.sql
@user_script/add_user.sql
@reservation_script/add_reservation.sql

-- Vérifications
@client_script/check_client.sql
@user_script/check_login.sql
@reservation_script/check_reservation.sql

-- Sélections
@client_script/get_all_clients.sql
@room_script/get_all_rooms.sql
@user_script/get_all_users.sql
@reservation_script/get_all_reservation.sql

-- Modifications
@room_script/modify_room.sql
@user_script/modify_user.sql
@reservation_script/modify_reservation.sql

-- Suppressions
@client_script/delete_client.sql
@user_script/delete_user.sql
@reservation_script/delete_reservation.sql
