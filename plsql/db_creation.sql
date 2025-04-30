-- Script to create the database and user for the hotel management system

create USER hote_user identified BY 2426;
GRANT CONNECT, RESOURCE TO "hotel_user";
ALTER USER "hotel_user" QUOTA UNLIMITED ON USERS;