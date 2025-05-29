-- creation de la base de donnees

CREATE USER hotel_user IDENTIFIED BY 2426;
GRANT CONNECT, RESOURCE TO hotel_user;
ALTER USER hotel_user QUOTA UNLIMITED ON USERS;
