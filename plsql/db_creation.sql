-- creation de la base de donnees

create USER hotel_user identified BY 2426;
GRANT CONNECT, RESOURCE TO "hotel_user";
ALTER USER "hotel_user" QUOTA UNLIMITED ON USERS;
