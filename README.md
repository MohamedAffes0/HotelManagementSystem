<div align="center">
    <img src="https://github.com/MohamedAffes0/HotelManagementSystem/blob/Mohamed/docs/images/logo-with-text.png" height="125px">
</div>

This project is developed as part of my studies in the integrated preparatory program MI2. It is a hotel management application that includes both administration and reception functionalities. The goal is to streamline the management of reservations, guests, rooms, and staff through an intuitive and efficient interface.

## Running the code
In order to run you need to install [maven](https://maven.apache.org/).
then you can simply run
```
mvn clean javafx:run
```
As for the database you need [Oracle DB](https://www.oracle.com/database/) alongside [SQL+](https://www.oracle.com/database/technologies/sqlplus-cloud.html).
Then simply run the SQL files in the PLSQL directory.

## Functionality
### Creating Accounts
A preexisting admin account needs to approve any new account and needs to assign them either an admin or receptionist status.
Only once the approval step is step is done can the user access the dashboard.
