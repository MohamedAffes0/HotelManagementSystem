<p align="center">
    <picture>
            <source media="(prefers-color-scheme: dark)" srcset="https://github.com/MohamedAffes0/HotelManagementSystem/blob/Mohamed/docs/images/logo-with-text-dark-mode.png">
            <source media="(prefers-color-scheme: light)" srcset="https://github.com/MohamedAffes0/HotelManagementSystem/blob/Mohamed/docs/images/logo-with-text-light-mode.png">
            <img src="https://github.com/MohamedAffes0/HotelManagementSystem/blob/Mohamed/docs/images/logo-with-text-light-mode.png">
    </picture>
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Made%20with-Java-red.svg">
    <img src="https://img.shields.io/badge/Using-OracleDB-red.svg">
    <img src="https://img.shields.io/badge/License-MIT-green.svg">
    <img src="https://img.shields.io/badge/Groupe-MI2A-blue.svg">
</p>

# Introduction
**Emerald Coast Dashboard** is a hotel management application that includes both administation and reception features. It was developed as the semester final project using **Java**, **JavaFX** , **[Oracle DB](https://www.oracle.com/database/)** and **[SQL*Plus](https://www.oracle.com/database/technologies/sqlplus-cloud.html)**.

This is a group project made by [Ayoub Ouali](https://github.com/the-viceroy), [Mohamed Affes](https://github.com/MohamedAffes0) and [Mayssa Ben M'rad](https://github.com/mayssabenmrad).

# Key Features
* Important statistics generation
* Reservations management
* Rooms management
* Clients management
* Accounts management

# Screenshot
<p>
    <img src="https://github.com/MohamedAffes0/HotelManagementSystem/blob/Mohamed/docs/images/screenshot.png">
</p>

# Getting started
## Required tools
Before starting you need to have the following tools in your development environment.
* [Maven](https://maven.apache.org/).
* [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/).
* [Oracle DB](https://www.oracle.com/database/).
* [SQL*Plus](https://www.oracle.com/database/technologies/sqlplus-cloud.html).

## Running the project
To run the project first use the following command to resolve dependencies
```
mvn dependency:resolve
```
then use to start the project
```
mvn clean javafx:run
```

# Project structure
## docs/
Houses project's documentation in Markdown format.
## models/
Houses the UML models used for conception. Refer to [Conception documentation](/docs/conception.md) for more details.
## plsql
Houses the Oracle PL/SQL scripts. Refer to [Database documentation](/docs/analyse_plsql.md) for more details.
## src/main
Houses the source code for the project and the needed assets for the gui app. Refer to [Java side documentation](/docs/analyse_java.md) for more details
