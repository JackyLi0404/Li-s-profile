# R18A_Group3_Assignment_2

## Set Up Database
populate the database to PostgreSQL with createDatabase.sql script

## Config Database connection
Using hibernate with JPA for ERM to connect with PostgreSQL.

modify src/main/resources/META-INF/persistence.xml on 
```
<property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/lab"/> <!-- BD Mane -->
<property name="javax.persistence.jdbc.user" value="name"/> <!-- DB User -->
<property name="javax.persistence.jdbc.password" value="password"/> <!-- DB Password -->
```
